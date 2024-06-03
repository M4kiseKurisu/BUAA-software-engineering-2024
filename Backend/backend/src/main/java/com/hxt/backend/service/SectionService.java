package com.hxt.backend.service;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.section.Section;
import com.hxt.backend.entity.section.Teacher;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.SectionAuthorityResponse;
import com.hxt.backend.response.sectionResponse.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private MessageMapper messageMapper;

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
            if (!academy.isEmpty() && (section.getAcademy() == null || !section.getAcademy().equals(academy))) {
                continue;
            }
            if (type != 0) {
                if (section.getType() == null
                        || (type == 1 && !section.getType().equals("一般专业课"))
                        || (type == 2 && !section.getType().equals("核心专业课"))
                        || (type == 3 && !section.getType().equals("一般通识课"))
                        || (type == 4 && !section.getType().equals("核心通识课"))
                        || (type == 5 && !section.getType().equals("基础类课程"))
                        || (type == 6 && !section.getType().equals("体育课"))
                        || (type == 7 && !section.getType().equals("其他课程"))
                        || (type == 8 && !section.getType().equals("板块通知"))) {
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

    public ArrayList<PostElement> getSectionPosts
            (Integer sectionId, String sort, String post_type, String tagName, Integer page) {
        Integer offset = (page - 1) * 5;
        List<Post> posts = null;
        ArrayList<PostElement> list = new ArrayList<>();
        if (post_type.equals("2")) {
            switch (sort) {
                case "0":
                    posts = tagName.isEmpty()? postMapper.getPostWithOffsetBySendTime(sectionId, offset)
                            : postMapper.getPostWithTagAndOffsetBySendTime(sectionId, tagName, offset);
                    break;
                case "2":
                    posts = tagName.isEmpty()? postMapper.getPostWithOffsetByFavorite(sectionId, offset)
                            : postMapper.getPostWithTagAndOffsetByFavorite(sectionId, tagName, offset);
                    break;
                case "1":
                    posts = tagName.isEmpty()? postMapper.getPostWithOffsetByLike(sectionId, offset)
                            : postMapper.getPostWithTagAndOffsetByLike(sectionId, tagName, offset);
                    break;
                case "3":
                    posts = tagName.isEmpty()? postMapper.getPostWithOffsetByReplyTime(sectionId, offset)
                            : postMapper.getPostWithTagAndOffsetByReplyTime(sectionId, tagName, offset);
                    break;
            }
        } else {
            switch (sort) {
                case "0":
                    posts = tagName.isEmpty()? postMapper.getPostWithCategoryAndOffsetBySendTime(sectionId, Integer.parseInt(post_type), offset)
                            : postMapper.getPostWithCategoryTagAndOffsetBySendTime(sectionId, Integer.parseInt(post_type), tagName, offset);
                    break;
                case "2":
                    posts = tagName.isEmpty()? postMapper.getPostWithCategoryAndOffsetByFavorite(sectionId, Integer.parseInt(post_type), offset)
                            : postMapper.getPostWithCategoryTagAndOffsetByFavorite(sectionId, Integer.parseInt(post_type), tagName, offset);
                    break;
                case "1":
                    posts = tagName.isEmpty()? postMapper.getPostWithCategoryAndOffsetByLike(sectionId, Integer.parseInt(post_type), offset)
                            : postMapper.getPostWithCategoryTagAndOffsetByLike(sectionId, Integer.parseInt(post_type), tagName, offset);
                    break;
                case "3":
                    posts = tagName.isEmpty()? postMapper.getPostWithCategoryAndOffsetByReplyTime(sectionId, Integer.parseInt(post_type), offset)
                            : postMapper.getPostWithCategoryTagAndOffsetByReplyTime(sectionId, Integer.parseInt(post_type), tagName, offset);
                    break;
            }
        }
        if (posts == null) {
            return list;
        }
        for (Post post: posts) {
            PostElement element = new PostElement();
            element.setPost_id(post.getPost_id());
            element.setAuthor_id(post.getAuthor_id());
            element.setAuthor_name(userMapper.getUserNameById(element.getAuthor_id()));
            element.setPost_title(post.getTitle());
            element.setPost_content(post.getContent());
            String[] time_send = post.getPostTime().toString().split(":");
            String postTime = time_send[0] + ":" + time_send[1];
            element.setPost_time("发布于 " + postTime);
            element.setPost_likes(post.getLike_count());
            element.setPost_favorites(post.getCollect_count());
            element.setPost_intro(post.getIntro());
            List<String> tags = postMapper.getTagNameByPost(element.getPost_id());
            element.setTag_list(tags);
            element.setPost_photo(post.getCover());

            list.add(element);
        }
        return list;
    }

    public Integer getPageCount(Integer sectionId, String post_type, String tagName) {
        Integer postCount = 0;
        if (post_type.equals("2")) {
            postCount = (tagName.isEmpty())? postMapper.getPostCountBySection(sectionId)
                    : postMapper.getPostCountByTagAndSection(sectionId, tagName);
        } else {
            postCount = (tagName.isEmpty())? postMapper.getPostCountByCategoryAndSection(sectionId, Integer.parseInt(post_type))
                    : postMapper.getPostCountByCategoryTagAndSection(sectionId, Integer.parseInt(post_type), tagName);
        }
        return (postCount - 1) / 5 + 1;
    }

    public ArrayList<PostElement> searchSectionPosts(Integer sectionId, String sort, String post_type,
                                                     String tagName, String keyword) {
        List<Post> posts;
        if (!post_type.equals("2")) {
            posts = postMapper.searchPostInSectionByKeywordTagTypeTimeDesc(sectionId, keyword, tagName, Integer.parseInt(post_type));
        } else {
            posts = postMapper.searchPostInSectionByKeywordTagTimeDesc(sectionId, keyword, tagName);
        }
        ArrayList<PostElement> list = new ArrayList<>();

        for (Post post: posts) {
            PostElement element = new PostElement();
            element.setPost_id(post.getPost_id());
            element.setAuthor_id(post.getAuthor_id());
            element.setAuthor_name(userMapper.getUserNameById(element.getAuthor_id()));
            element.setPost_title(post.getTitle());
            element.setPost_content(post.getContent());
            element.setPost_reply_time(postMapper.getReplyTime(post.getPost_id()));

            String[] time_send = post.getPostTime().toString().split(":");
            String postTime = time_send[0] + ":" + time_send[1];
            element.setPost_time("发布于 " + postTime);

            element.setPost_likes(post.getLike_count());
            element.setPost_favorites(post.getCollect_count());
            element.setPost_intro(post.getIntro());
            List<String> tags = postMapper.getTagNameByPost(element.getPost_id());
            element.setTag_list(tags);
            element.setPost_photo(post.getCover());

            /*
            List<Integer> imageId = postMapper.getImageIdByPost(element.getPost_id());
            for (Integer id: imageId) {
                if (imageMapper.getImage(id) != null) {
                    element.setPost_photo(imageMapper.getImage(id));
                    break;
                }
            }

             */

            list.add(element);
        }

        if (!tagName.isEmpty()) {
            list = list.stream().filter(e -> e.getTag_list().contains(tagName)).collect(Collectors.toCollection(ArrayList::new));
        }

        switch (sort) {
            case "0" -> Collections.reverse(list);
            case "1" -> list.sort((o1, o2) -> o2.getPost_likes().compareTo(o1.getPost_likes()));
            case "2" -> list.sort((o1, o2) -> o2.getPost_favorites().compareTo(o1.getPost_favorites()));
            case "3" -> list.sort((o1, o2) -> o2.getPost_reply_time().compareTo(o1.getPost_reply_time()));
        }

        return list;
    }

    public SectionInfoResponse getSectionInfo(Integer sectionId, Integer userId) {
        Section section = sectionMapper.selectSectionById(sectionId);
        SectionInfoResponse response;
        if (section == null) {
            response = new SectionInfoResponse();
            response.setSuccess(false);
            return response;
        }
        ArrayList<Teacher> teachers = sectionMapper.selectTeacherBySectionId(sectionId);
        ArrayList<TeacherElement> t = new ArrayList<>();
        for (Teacher teacher: teachers) {
            t.add(new TeacherElement(teacher.getTeacher_name(),teacher.getTeacher_intro()));
        }
        response = new SectionInfoResponse(
                true, getFocusState(userId,sectionId),
                section.getName(),
                section.getType(),
                section.getCredit(),
                section.getCapacity(),
                sectionMapper.getFollowCountBySectionId(sectionId),
                sectionMapper.getPostCountBySectionId(sectionId),
                section.getIntro(),
                section.getAcademy(), t,
                sectionMapper.selectAssistantBySectionId(sectionId)
        );
        return response;
    }

    public SectionAuthorityResponse getSectionAuthority(Integer id) {
        return new SectionAuthorityResponse(
                adminMapper.getSectionTeacherAuthority(id),
                adminMapper.getSectionAssistantAuthority(id),
                adminMapper.getSectionOthersAuthority(id)
        );
    }

    public boolean checkCourseName(String name) {
        return sectionMapper.getSectionIdByName(name) != null;
    }

    public Integer addCourse(String name, String intro, String type,
                             String academy, Integer credit, Integer capacity) {
        sectionMapper.insertCourse(name, intro, type, academy, credit, capacity);
        return sectionMapper.getSectionIdByName(name);
    }

    public boolean addCourseRequest(String name, String intro, String type,
                                     String academy, Integer credit, Integer capacity) {
        return sectionMapper.insertCourseRequest(name, intro, type, academy, credit, capacity) > 0;
    }

    public BasicInfoResponse setSectionInfo(Integer id, String name, String intro, String type,
                                               String academy, Integer credit, Integer capacity) {
        boolean nameSuccess = (name == null) || sectionMapper.setCourseName(id, name) > 0;
        boolean introSuccess = (intro == null) || sectionMapper.setCourseIntro(id, intro) > 0;
        boolean typeSuccess = (type == null) || sectionMapper.setCourseType(id, type) > 0;
        boolean academySuccess = (academy == null) || sectionMapper.setCourseAcademy(id, academy) > 0;
        boolean creditSuccess = (credit == null) || sectionMapper.setCourseCredit(id, credit) > 0;
        boolean capacitySuccess = (capacity == null) || sectionMapper.setCourseCapacity(id, capacity) > 0;
        boolean res = nameSuccess && introSuccess && typeSuccess && academySuccess && creditSuccess && capacitySuccess;
        String info = res? "" : "操作失败，可能是服务器错误！";
        return new BasicInfoResponse(res, info);
    }

    public BasicInfoResponse tryAddAssistant(Integer user, Integer section, Integer assistant) {
        String type = adminMapper.checkAuthorityType(user, section);
        if (type == null || !type.equals("teacher")) {
            return new BasicInfoResponse(false, "您没有该板块的教师身份！");
        }
        adminMapper.setAuthority(assistant, section, "assistant");
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageMapper.sendSystemNoticeToUser("授权通知",
                String.format("您于 %s 被授予 %s 版块的 %s 权限，希望您为板块建设贡献自己的一份力量。",
                        df.format(date), sectionMapper.getSectionNameById(section), "assistant"), assistant);
        return new BasicInfoResponse(true, "");
    }

    public BasicInfoResponse tryDeleteAssistant(Integer user, Integer section, Integer assistant) {
        String type = adminMapper.checkAuthorityType(user, section);
        if (type == null || !type.equals("teacher")) {
            return new BasicInfoResponse(false, "您没有该板块的教师身份！");
        }
        adminMapper.deleteAuthority(assistant, section);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageMapper.sendSystemNoticeToUser("收回权限通知",
                String.format("您在 %s 版块的权限于 %s 被收回。",
                        sectionMapper.getSectionNameById(section), df.format(date)), assistant);
        return new BasicInfoResponse(true, "");
    }

    public BasicInfoResponse tryBlockUser(boolean isAdmin, Integer user, Integer section, Integer id, Integer days) {
        if ((adminMapper.checkAuthorityType(user, section) == null) && !isAdmin) {
            return new BasicInfoResponse(false, "您没有该板块的教师或助教身份！");
        }
        if (days == null) {
            days = Integer.MAX_VALUE;
        }
        String type = adminMapper.checkAuthorityType(user, section);
        if (type == null || !type.equals("teacher")) {
            return new BasicInfoResponse(false, "您没有该板块的教师身份！");
        }
        if (userMapper.updateSectionBlock(id, section, days) == 0) {
            userMapper.sectionBlockUser(id, section, days);
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageMapper.sendSystemNoticeToUser("封禁通知",
                String.format("您因违反有关规定，被 %s 版块管理员于 %s 在该板块封禁 %d 天（自本通知中的时间点起计算）。",
                        sectionMapper.getSectionNameById(section) ,df.format(date), days), id);
        return new BasicInfoResponse(true, "");
    }

    public BasicInfoResponse tryUnblockUser(Integer user, Integer section, Integer id) {
        if (adminMapper.checkAuthorityType(user, section) == null) {
            return new BasicInfoResponse(false, "您没有该板块的教师或助教身份！");
        }
        userMapper.sectionUnblockUser(id, section);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageMapper.sendSystemNoticeToUser("解封通知",
                String.format("您于 %s 被 %s 版块管理员在该板块手动解封。",
                        df.format(date), sectionMapper.getSectionNameById(section)), id);
        return new BasicInfoResponse(true, "");
    }

    public SectionAcademyResponse getAcademyList() {
        return new SectionAcademyResponse(sectionMapper.getAllAcademy());
    }
}
