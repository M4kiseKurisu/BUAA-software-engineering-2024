package com.hxt.backend.service;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.section.Section;
import com.hxt.backend.entity.section.Teacher;
import com.hxt.backend.mapper.ImageMapper;
import com.hxt.backend.mapper.PostMapper;
import com.hxt.backend.mapper.SectionMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.sectionResponse.PostElement;
import com.hxt.backend.response.sectionResponse.SectionElement;
import com.hxt.backend.response.sectionResponse.SectionInfoResponse;
import com.hxt.backend.response.sectionResponse.TeacherElement;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private ImageMapper imageMapper;

    public ArrayList<SectionElement> getHotSections(Integer userId) {
        ArrayList<Section> sections = sectionMapper.selectAllSection();
        ArrayList<SectionElement> list = new ArrayList<>();
        for (Section section: sections) {
            SectionElement element = new SectionElement();
            element.setSection_id(section.getSection_id());
            element.setSection_name(section.getName());
            element.setSection_introduction(section.getIntro());
            element.setSection_academy(section.getAcademy());
            element.setSection_type(section.getType());
            element.setSection_follower_count(sectionMapper.getFollowCountBySectionId(element.getSection_id()));
            element.setSection_is_following(getFocusState(userId, section.getSection_id()));
            list.add(element);
        }

        list.sort((o1, o2) -> o2.getSection_follower_count().compareTo(o1.getSection_follower_count()));

        return list;
    }

    public ArrayList<SectionElement> searchSection(String keyWord, Integer sort, Integer type, String academy, Integer userId) {
        ArrayList<Section> sections;
        if (keyWord.isEmpty()) {
            sections = sectionMapper.selectAllSection();
        }
        else {
            sections = sectionMapper.selectSectionByName(keyWord);
        }
        ArrayList<SectionElement> list = new ArrayList<>();
        for (Section section: sections) {
            if (!academy.isEmpty() && !section.getAcademy().equals(academy)) {
                continue;
            }
            if (type != 0) {
                if (type == 1 && !section.getType().equals("一般专业课")) {
                    continue;
                }
                if (type == 2 && !section.getType().equals("核心专业课")) {
                    continue;
                }
            }
            SectionElement element = new SectionElement();
            element.setSection_id(section.getSection_id());
            element.setSection_name(section.getName());
            element.setSection_introduction(section.getIntro());
            element.setSection_academy(section.getAcademy());
            element.setSection_type(section.getType());
            element.setSection_follower_count(sectionMapper.getFollowCountBySectionId(element.getSection_id()));
            element.setSection_is_following(getFocusState(userId, section.getSection_id()));
            list.add(element);
        }

        list.sort((o1, o2) -> o2.getSection_follower_count().compareTo(o1.getSection_follower_count()));

        return list;
    }

    public boolean focusSection(Integer userId, Integer sectionId) {
        if (getFocusState(userId, sectionId)) {
            return false;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        sectionMapper.insertUserFocusSection(userId, sectionId, timestamp);
        return true;
    }

    public boolean unfocusSection(Integer userId, Integer sectionId) {
        if (!getFocusState(userId, sectionId)) {
            return false;
        }
        sectionMapper.deleteUserFocusSection(userId, sectionId);
        return true;
    }

    public boolean getFocusState(Integer userId, Integer sectionId) {
        Integer state = sectionMapper.getUserSectionFocusState(userId,sectionId);
        return state != 0;
    }

    public ArrayList<PostElement> getSectionPosts(Integer sectionId, String sort, String post_type, String tagName) {
        List<Post> posts = sectionMapper.selectPostBySectionId(sectionId);
        ArrayList<PostElement> list = new ArrayList<>();

        if (!post_type.equals("2")) {
            posts = posts.stream().filter(e -> e.getCategory().equals(Integer.parseInt(post_type))).collect(Collectors.toList());
        }

        for (Post post: posts) {
            PostElement element = new PostElement();
            element.setPost_id(post.getPost_id());
            element.setAuthor_id(post.getAuthor_id());
            element.setAuthor_name(userMapper.getUserNameById(element.getAuthor_id()));
            element.setPost_title(post.getTitle());
            element.setPost_content(post.getContent());
            element.setPost_time(post.getPostTime());
            element.setPost_likes(post.getLike_count());
            element.setPost_favorites(post.getCollect_count());
            List<String> tags = postMapper.getTagNameByPost(element.getPost_id());
            element.setTag_list(tags);
            element.setPost_photo("");

            List<Integer> imageId = postMapper.getImageIdByPost(element.getPost_id());
            for (Integer id: imageId) {
                if (imageMapper.getImage(id) != null) {
                    element.setPost_photo(imageMapper.getImage(id));
                    break;
                }
            }

            list.add(element);
        }

        if (!tagName.isEmpty()) {
            list = list.stream().filter(e -> e.getTag_list().contains(tagName)).collect(Collectors.toCollection(ArrayList::new));
        }

        switch (sort) {
            case "0" -> Collections.reverse(list);
            case "1" -> list.sort((o1, o2) -> o2.getPost_likes().compareTo(o1.getPost_likes()));
            case "2" -> list.sort((o1, o2) -> o2.getPost_favorites().compareTo(o1.getPost_favorites()));
        }

        return list;
    }

    public SectionInfoResponse getSectionInfo(Integer sectionId) {
        Section section = sectionMapper.selectSectionById(sectionId);
        SectionInfoResponse response = new SectionInfoResponse();
        if (section == null) {
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        response.setCourse_name(section.getName());
        response.setCourse_type(section.getType());
        response.setCourse_credit(section.getCredit());
        response.setCourse_capacity(section.getCapacity());
        response.setCourse_follows(sectionMapper.getFollowCountBySectionId(sectionId));
        response.setCourse_posts(sectionMapper.getPostCountBySectionId(sectionId));
        response.setCourse_info(section.getIntro());
        response.setCourse_college(section.getAcademy());
        ArrayList<Teacher> teachers = sectionMapper.selectTeacherBySectionId(sectionId);
        ArrayList<TeacherElement> t = new ArrayList<>();
        for (Teacher teacher: teachers) {
            t.add(new TeacherElement(teacher.getTeacher_name(),teacher.getTeacher_intro()));
        }
        response.setTeachers(t);
        response.setAssistants(sectionMapper.selectAssistantBySectionId(sectionId));
        return response;
    }
}
