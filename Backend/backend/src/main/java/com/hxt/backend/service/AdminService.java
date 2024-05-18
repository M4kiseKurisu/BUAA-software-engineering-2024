package com.hxt.backend.service;

import com.hxt.backend.entity.Report;
import com.hxt.backend.entity.User;
import com.hxt.backend.entity.group.Group;
import com.hxt.backend.entity.post.Comment;
import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.post.Reply;
import com.hxt.backend.entity.section.Section;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.group.GroupElement;
import com.hxt.backend.response.list.TimeInfoResponse;
import com.hxt.backend.response.list.ReportListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.list.UserSectionBlockListResponse;
import com.hxt.backend.response.sectionResponse.SectionElement;
import com.hxt.backend.response.singleInfo.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PostMapper postMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private ImageMapper imageMapper;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private CheckInMapper checkInMapper;

    @Resource
    private PostService postService;

    private final String defaultHeadUrl = "https://hxt-2024.obs.cn-north-4.myhuaweicloud.com:443/6059d059-907d-4b80-a351-4549cdaf6ce6-R-C.jpg";
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Integer checkPassword(String name, String password) {
        Timestamp lock = adminMapper.checkLock(name);
        if (lock != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.getTime() - lock.getTime() < 1000 * 60 * 60 * 24) {
                return -4;
            }
        }
        //String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        Integer id = adminMapper.tryLogin(name, password);
        if (id != null) {
            adminMapper.setFail(name, 0);
            return id;
        }
        Integer fail = adminMapper.checkFail(name);
        if (fail == null) {
            return -5;
        } else if (fail < 3) {
            adminMapper.setFail(name, fail + 1);
            return -(fail + 1);
        } else {
            adminMapper.setFail(name, 0);
            adminMapper.setLock(name, new Timestamp(System.currentTimeMillis()));
            return -4;
        }
    }

    public TotalInfoResponse getTotalInfo() {
        long tmp = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        Integer recent24int = Math.toIntExact(tmp / 1000);
        Timestamp recent24 = new Timestamp(tmp);
        Integer post24 = postMapper.getPostNumRecent(recent24);
        Integer postAll = postMapper.getPostNum();
        return new TotalInfoResponse(
                userMapper.getLoginNumRecent(recent24int),
                post24,
                post24 + postMapper.getCommentNumRecent(recent24) + postMapper.getReplyNumRecent(recent24),
                userMapper.getUserNum(),
                sectionMapper.getCourseNum(),
                sectionMapper.getSchoolNum(),
                postAll,
                postAll + postMapper.getCommentNum() + postMapper.getReplyNum(),
                groupMapper.getGroupCount(),
                checkInMapper.getCheckInCount()
        );
    }

    public TimeInfoResponse getTimeInfo() {
        long now = System.currentTimeMillis();
        TimeInfoResponse res = new TimeInfoResponse(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        long hourStart = now / 3600000 * 3600000;
        for (int i = 0; i < 24; i++) {
            Timestamp start = new Timestamp(hourStart - i * 3600000);
            Timestamp end = new Timestamp(hourStart - (i - 1) * 3600000);
            int postNum = postMapper.getPostNumRange(start, end);
            int commentNum = postNum + postMapper.getCommentNumRange(start, end)
                    + postMapper.getReplyNumRange(start, end);
            int checkinNum = checkInMapper.getCheckInNumRange(start, end);
            res.getPost_24h().add(0, new TimeValueResponse(df.format(start), postNum));
            res.getComment_24h().add(0, new TimeValueResponse(df.format(start), commentNum));
            res.getCheckin_24h().add(0, new TimeValueResponse(df.format(start), checkinNum));
        }
        long dayStart = now / 86400000 * 86400000;
        for (int i = 0; i < 30; i++) {
            Timestamp start = new Timestamp(dayStart - i * 86400000L);
            Timestamp end = new Timestamp(dayStart - (i - 1) * 86400000L);
            int postNum = postMapper.getPostNumRange(start, end);
            int commentNum = postNum + postMapper.getCommentNumRange(start, end)
                    + postMapper.getReplyNumRange(start, end);
            int checkinNum = checkInMapper.getCheckInNumRange(start, end);
            res.getPost_30d().add(0, new TimeValueResponse(df.format(start), postNum));
            res.getComment_30d().add(0, new TimeValueResponse(df.format(start), commentNum));
            res.getCheckin_30d().add(0, new TimeValueResponse(df.format(start), checkinNum));
        }
        return res;
    }

    public boolean checkPassword(Integer id, String password) {
        //String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        return adminMapper.checkPassword(id, password) > 0;
    }

    public void resetPassword(Integer id, String password) {
        //String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        adminMapper.resetPassword(id, password);
    }

    public boolean addCourse(String name, String intro, String type,
                             String academy, Integer credit, Integer capacity) {
        return sectionMapper.insertCourse(name, intro, type, academy, credit, capacity) > 0;
    }

    public boolean addSchool(String name, String intro, String category, String web) {
        return sectionMapper.insertSchool(name, intro, category, web) > 0;
    }

    public String deleteSection(Integer id, Integer moveId) {
        if (sectionMapper.getSectionNameById(id) == null) {
            return "欲删除的板块不存在！";
        }
        if (moveId != null && moveId != 0 && sectionMapper.getSectionNameById(moveId) == null) {
            return "移动到的目标版块不存在！";
        }
        sectionMapper.removeSectionFollow(id);
        sectionMapper.removeSectionTeacher(id);
        sectionMapper.removeSectionAuthority(id);
        List<Post> posts = sectionMapper.selectPostBySectionId(id);
        Date date = new Date();
        if (moveId == null || moveId == 0) {
            for (Post post : posts) {
                postService.deletePost(0, post.getPost_id(), true);
            }
        } else {
            for (Post post : posts) {
                postMapper.movePostSection(post.getPost_id(), moveId);
                messageMapper.sendSystemNoticeToUser("移动通知",
                        String.format("您发表的帖子 “%s” 于 %s 被移动至板块 %s", post.getTitle(), df.format(date),
                                sectionMapper.getSectionNameById(moveId)),
                        post.getAuthor_id());
            }
        }
        sectionMapper.removeSection(id);
        return "";
    }

    public boolean blockUser(Integer id, Integer days) {
        Integer realtime = days == null? Integer.MAX_VALUE : days;
        Date date = new Date();
        messageMapper.sendSystemNoticeToUser("封禁通知",
                String.format("您因违反有关规定，被管理员于 %s 被封禁 %d 天（自本通知中的时间点起计算）。",
                        df.format(date), realtime), id);
        return userMapper.globalBlockUser(id, realtime) > 0;
    }

    public boolean unblockUser(Integer id) {
        return userMapper.globalUnblockUser(id) > 0;
    }

    public boolean sectionUnblockUser(Integer id, Integer section) {
        return userMapper.sectionUnblockUser(id, section) > 0;
    }

    public UserListResponse getUserList() {
        List<Integer> ids = userMapper.selectAllUserId();
        UserListResponse userInfoResponse = new UserListResponse(ids.size(), new ArrayList<>());
        for (Integer id : ids) {
            Integer tmp = userMapper.isGlobalBlocked(id);
            boolean isBlocked = (tmp != null && tmp != 0);
            User user = userMapper.selectUserById(id);
            List<UserAuthorityInfo> authorityInfo = new ArrayList<>();
            Integer check = adminMapper.checkGlobalAuthority(id);
            if (check != null && check > 0) {
                authorityInfo.add(new UserAuthorityInfo(0, "全局管理员"));
            } else {
                authorityInfo = adminMapper.getUserAuthorities(id);
            }
            userInfoResponse.getUser().add(new UserSocialInfoResponse(
                    user.getName(), id,
                    (user.getHeadId() == null) ? defaultHeadUrl : imageMapper.getImage(user.getHeadId()),
                    null, null, null, null, null, user.getSign(), null, isBlocked, authorityInfo
            ));
        }
        return userInfoResponse;
    }

    public ArrayList<SectionElement> getSectionList() {
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
            element.setSection_is_following(false);
            list.add(element);
        }
        return list;
    }

    public UserSectionBlockListResponse getSectionBlockList(Integer order) {
        List<UserSectionBlockResponse> response = new ArrayList<>();
        if (order == 0) {
            response = userMapper.getSectionBlockListOrderByTime();
        } else if (order == 1) {
            response = userMapper.getSectionBlockListOrderByUser();
        } else if (order == 2) {
            response = userMapper.getSectionBlockListOrderBySection();
        }
        for (UserSectionBlockResponse u : response) {
            u.setSection_name(sectionMapper.getSectionNameById(u.getSection_id()));
            u.setUser_name(userMapper.getUserNameById(u.getUser_id()));
            u.setBlock_time(df.format(u.getBlock_timestamp()));
        }
        return new UserSectionBlockListResponse(response.size(), response);
    }

    public ReportListResponse getUnhandledReports(Integer type) {
        List<Report> reports = adminMapper.getUnhandledReports(type);
        ReportListResponse response = new ReportListResponse(reports.size(), new ArrayList<>());
        switch (type) {
            case 0:
                for (Report report : reports) {
                    Integer postId = report.getTarget();
                    Post post = postMapper.getPost(postId);
                    response.getReport().add(new ReportResponse(
                            report.getReportId(), postId, post.getContent(), null,
                            post.getAuthor_id(), report.getUserId(), report.getDetail(), report.getResource()
                    ));
                }
                break;
            case 1:
                for (Report report : reports) {
                    Integer commentId = report.getTarget();
                    Comment comment = postMapper.getCommentById(commentId);
                    response.getReport().add(new ReportResponse(
                            report.getReportId(), commentId, comment.getContent(),
                            comment.getPost_id(), comment.getAuthor_id(),
                            report.getUserId(), report.getDetail(), report.getResource()
                    ));
                }
                break;
            case 2:
                for (Report report : reports) {
                    Integer replyId = report.getTarget();
                    Reply reply = postMapper.getReplyById(replyId);
                    response.getReport().add(new ReportResponse(
                            report.getReportId(), replyId, reply.getContent(),
                            postMapper.getCommentById(reply.getComment_id()).getPost_id(),
                            reply.getAuthor_id(),
                            report.getUserId(), report.getDetail(), report.getResource()
                    ));
                }
                break;
            case 3:
                for (Report report : reports) {
                    Integer userId = report.getTarget();
                    response.getReport().add(new ReportResponse(
                            report.getReportId(), userId, null, null, null,
                            report.getUserId(), report.getDetail(), report.getResource()
                    ));
                }
                break;
            case 4:
                for (Report report : reports) {
                    Integer sectionId = report.getTarget() / 3;
                    Integer typeNum = report.getTarget() % 3;
                    String authorityType = (typeNum == 0)? "teacher" :
                            (typeNum == 1)? "assistant" : "";
                    response.getReport().add(new ReportResponse(
                            report.getReportId(), sectionId, authorityType, null, null,
                            report.getUserId(), report.getDetail(), report.getResource()
                    ));
                }
                break;
        }
        return response;
    }

    public ArrayList<SectionElement> getCourseApply() {
        ArrayList<Section> sections = sectionMapper.selectAllSectionRequest();
        ArrayList<SectionElement> list = new ArrayList<>();
        for (Section section: sections) {
            SectionElement element = new SectionElement();
            element.setSection_id(section.getSection_id());
            element.setSection_name(section.getName());
            element.setSection_introduction(section.getIntro());
            element.setSection_academy(section.getAcademy());
            element.setSection_type(section.getType());
            element.setSection_follower_count(0);
            element.setSection_is_following(false);
            list.add(element);
        }
        return list;
    }

    public boolean handleReport(Integer reportId, boolean choice, Integer days) {
        Report report = adminMapper.getSingleReport(reportId);
        if (report == null) {
            return false;
        }
        if (choice) {
            switch (report.getType()) {
                case 0:
                    Integer postId = report.getTarget();
                    postService.deletePost(0, postId, true);
                    break;
                case 1:
                    Integer commentId = report.getTarget();
                    postService.deleteComment(true, 0, commentId);
                    break;
                case 2:
                    Integer replyId = report.getTarget();
                    postService.deleteReply(true, 0, replyId);
                    break;
                case 3:
                    Integer userId = report.getTarget();
                    blockUser(userId, days);
                    break;
                case 4:
                    Integer user = report.getUserId();
                    Integer sectionId = report.getTarget() / 3;
                    Integer typeNum = report.getTarget() % 3;
                    if (sectionId == 0 && typeNum == 0) {
                        setGlobalAuthority(user);
                    }
                    String authorityType = (typeNum == 0)? "teacher" :
                            (typeNum == 1)? "assistant" : "";
                    setAuthority(user, sectionId, authorityType);
                    break;
            }
        }
        if (report.getType() < 4) {
            List<Integer> sameIds = adminMapper.getSameTargetReports(report.getType(), report.getTarget());
            for (Integer sameId : sameIds) {
                adminMapper.handleReport(sameId, (choice? 2 : 0));
            }
        } else {
            adminMapper.handleReport(reportId, (choice? 2 : 0));
        }
        return true;
    }

    public boolean handleCourseRequest(Integer id, Boolean choice) {
        if (choice) {
            return adminMapper.handleSection(id) > 0;
        } else {
            return sectionMapper.removeSection(id) > 0;
        }
    }

    public boolean setAuthority(Integer id, Integer section, String type) {
        if (checkAuthority(id, section)) {
            return true;
        }
        Date date = new Date();
        messageMapper.sendSystemNoticeToUser("授权通知",
                String.format("您于 %s 被授予 %s 版块的 %s 权限，希望您为板块建设贡献自己的一份力量。",
                        df.format(date), sectionMapper.getSectionNameById(section), type), id);
        return adminMapper.setAuthority(id, section, type) > 0;
    }

    public boolean deleteAuthority(Integer id, Integer section) {
        Date date = new Date();
        messageMapper.sendSystemNoticeToUser("收回权限通知",
                String.format("您在 %s 版块的权限于 %s 被收回。",
                        sectionMapper.getSectionNameById(section), df.format(date)), id);
        return adminMapper.deleteAuthority(id, section) > 0;
    }

    public boolean checkAuthority(Integer id, Integer section) {
        if (checkGlobalAuthority(id)) {
            return true;
        }
        return adminMapper.checkAuthority(id, section) != null;
    }

    public boolean setGlobalAuthority(Integer id) {
        Date date = new Date();
        messageMapper.sendSystemNoticeToUser("授权通知",
                String.format("您于 %s 被授予全局教师权限，希望您为航学通平台建设贡献自己的一份力量。",
                        df.format(date)), id);
        return adminMapper.setGlobalAuthority(id) > 0;
    }

    public boolean deleteGlobalAuthority(Integer id) {
        Date date = new Date();
        messageMapper.sendSystemNoticeToUser("收回权限通知",
                String.format("您的全局管理权限于 %s 被收回。", df.format(date)), id);
        return adminMapper.deleteGlobalAuthority(id) > 0;
    }

    public boolean checkGlobalAuthority(Integer id) {
        return adminMapper.checkGlobalAuthority(id) > 0;
    }

    public void setUserCookie(String type, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(type, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public boolean lengthCheck(String s, int min, int max) {
        return (s.length() >= min) && (s.length() <= max);
    }
}
