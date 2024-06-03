package com.hxt.backend;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.mapper.AdminMapper;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.PostMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.util.List;

//  定时任务
@Configuration
@Slf4j
@EnableScheduling
public class ScheduleTaskConfig {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private PostMapper postMapper;

    @Scheduled(cron = "0 0 0/8 * * *")   //  每天0/8/16时
    private void clearLog() {
        log.info("清理8小时前的用户操作记录...");
        adminMapper.deleteOldLogs(new Timestamp(System.currentTimeMillis() - 8 * 60 * 60 * 1000));
    }

    @Scheduled(cron = "0 0 4 * * *")   //  每天4时
    private void clearHandled() {
        log.info("清理已处理完毕的举报...");
        adminMapper.deleteHandledReport();
    }

    @Scheduled(cron = "0 0 4 * * *")   //  每天4时
    private void clearPushedMessage() {
        log.info("清理7天前的已推送已读系统通知...");
        messageMapper.deleteOldReadMessage();
        messageMapper.deleteNoRefSystemNotice();
        log.info("清理10天前的回复通知...");
        messageMapper.deleteOldReplyNotice();
    }

    @Scheduled(cron = "0 0 4 * * *")   //  每天4时
    private void setPostReplyTime() {
        log.info("同步帖子最新回复时间...");
        List<Post> posts = postMapper.getAllPost();
        for (Post post : posts) {
            Timestamp t1 = postMapper.getLastCommentTime(post.getPost_id());
            if (t1 != null) {
                Timestamp t2 = postMapper.getLastReplyTime(post.getPost_id());
                Timestamp t;
                if (t2 != null) {
                    t = t1.after(t2)? t1 : t2;
                } else {
                    t = t1;
                }
                postMapper.resetReplyTime(post.getPost_id(), t);
            } else {
                postMapper.resetReplyTime(post.getPost_id(), post.getPostTime());
            }
        }
    }
}
