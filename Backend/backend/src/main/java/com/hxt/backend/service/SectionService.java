package com.hxt.backend.service;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.section.Section;
import com.hxt.backend.mapper.SectionMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.sectionResponse.PostElement;
import com.hxt.backend.response.sectionResponse.SectionElement;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private UserMapper userMapper;

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

        Collections.sort(list, new Comparator<SectionElement>() {
            @Override
            public int compare(SectionElement o1, SectionElement o2) {
                return o2.getSection_follower_count().compareTo(o1.getSection_follower_count());
            }
        });

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

        Collections.sort(list, new Comparator<SectionElement>() {
            @Override
            public int compare(SectionElement o1, SectionElement o2) {
                return o2.getSection_follower_count().compareTo(o1.getSection_follower_count());
            }
        });

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
        ArrayList<Post> posts = sectionMapper.selectPostBySectionId(sectionId);
        ArrayList<PostElement> list = new ArrayList<>();



        for (Post post: posts) {
            PostElement element = new PostElement();
            element.setPost_id(post.getPost_id());
            element.setAuthor_id(post.getAuthorId());
            element.setAuthor_name(userMapper.getUserNameById(element.getAuthor_id()));
            element.setPost_title(post.getTitle());
            element.setPost_content(post.getContent());
            element.setPost_time(post.getPostTime());
            // likes favorites tags photo
            ArrayList<String> tags = new ArrayList<>();



            element.setTag_list(tags);
            list.add(element);
        }

        return list;
    }

}
