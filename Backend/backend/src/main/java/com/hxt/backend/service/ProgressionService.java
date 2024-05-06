package com.hxt.backend.service;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.progression.School;
import com.hxt.backend.entity.section.Section;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.postResponse.PostIntroResponse;
import com.hxt.backend.response.progressionResponse.PostListResponse;
import com.hxt.backend.response.progressionResponse.SchoolInfoResponse;
import com.hxt.backend.response.progressionResponse.SchoolIntroResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressionService {
    @Resource
    private PostMapper postMapper;
    
    @Resource
    private SectionMapper sectionMapper;
    
    @Resource
    private ProgressionMapper progressionMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private ImageMapper imageMapper;
    
    
    //获取模块下帖子总数
    public Integer getTotalPostNum(Integer sectionId) {
        return sectionMapper.getPostCountBySectionId(sectionId);
    }
    
    
    //获取模块下热门作者id
    public List<Integer> getHotAuthorId(Integer sectionId) {
        return progressionMapper.getHotAuthorId(sectionId);
    }
    
    //获取模块下所有帖子
    public List<PostIntroResponse> getTotalPost(Integer sectionId) {
        List<Post> posts = sectionMapper.selectPostBySectionId(sectionId);
        
        return getPostIntroResponseByPost(posts);
    }
    
    
    //筛选帖子
    public List<PostIntroResponse> filterPost(String target, Integer type, String keyword, Integer sort) {
        List<Post> posts;
        
        if (sort == 0) {
            if (type == 0 || type == 1) {
                posts = postMapper.searchPostInSectionByKeywordTagTypeHotDesc(0, keyword, target, type);
            } else {
                posts = postMapper.searchPostInSectionByKeywordTagHotDesc(0, keyword, target);
            }
        } else {
            if (type == 0 || type == 1) {
                posts = postMapper.searchPostInSectionByKeywordTagTypeTimeDesc(0, keyword, target, type);
            } else {
                posts = postMapper.searchPostInSectionByKeywordTagTimeDesc(0, keyword, target);
            }
        }
        
        return getPostIntroResponseByPost(posts);
    }
    
    
    
    //获取学校列表
    public List<SchoolIntroResponse> getSchoolList() {
        List<SchoolIntroResponse> schoolIntroResponses = new ArrayList<>();
        
        List<School> schools = progressionMapper.getTotalSchool();
        
        for (School school : schools) {
            SchoolIntroResponse schoolIntroResponse = new SchoolIntroResponse(school);
            
            schoolIntroResponses.add(schoolIntroResponse);
        }
        
        return schoolIntroResponses;
    }
    
    
    //获取学校详细信息
    public SchoolInfoResponse getSchoolInfo(Integer school_id) {
        School school = progressionMapper.getSchoolById(school_id);
        if (school == null) {
            return new SchoolInfoResponse(false, null, null, null, null, null);
        }
        
        SchoolInfoResponse schoolInfoResponse = new SchoolInfoResponse(school);
        
        //获取学校名和包含此tag的帖子
        String schoolName = school.getName();
        List<Post> posts = postMapper.getPostByTagName(schoolName);
        
        List<PostIntroResponse> postIntroResponses = getPostIntroResponseByPost(posts);
        
        schoolInfoResponse.setPosts(postIntroResponses);
        schoolInfoResponse.setSuccess(true);
        
        return schoolInfoResponse;
    
    }
    
    
    public List<PostIntroResponse> getPostIntroResponseByPost(List<Post> posts) {
        List<PostIntroResponse> postIntroResponses = new ArrayList<>();
        
        for (Post post : posts) {
            PostIntroResponse postIntroResponse = new PostIntroResponse(post);
            String authorName = userMapper.getUserNameById(post.getAuthor_id());
            List<String> tags = postMapper.getTagNameByPost(post.getPost_id());
            
            String imageUrl = null;
            List<Integer> imageIds = postMapper.getImageIdByPost(post.getPost_id());
            if (!imageIds.isEmpty()) {
                imageUrl = imageMapper.getImage(imageIds.get(0));
            }
            
            postIntroResponse.setAuthor_name(authorName);
            postIntroResponse.setTags(tags);
            postIntroResponse.setPost_image(imageUrl);
            
            
            postIntroResponses.add(postIntroResponse);
        }
        return postIntroResponses;
    }
    

}
