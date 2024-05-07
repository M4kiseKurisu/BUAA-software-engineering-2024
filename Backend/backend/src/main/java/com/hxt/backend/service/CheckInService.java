package com.hxt.backend.service;

import com.hxt.backend.entity.checkIn.CheckIn;
import com.hxt.backend.mapper.CheckInMapper;
import com.hxt.backend.response.checkInResponse.CheckInIntroListResponse;
import com.hxt.backend.response.checkInResponse.CheckInIntroResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInService {
    @Resource
    private CheckInMapper checkInMapper;
    
    public List<CheckInIntroResponse> getPyqList(Integer userId) {
        List<CheckInIntroResponse> checkInIntroResponses = new ArrayList<>();
        
        List<CheckIn> checkIns = checkInMapper.getCheckInByAuthorId(userId);
        for (CheckIn checkIn : checkIns) {
            CheckInIntroResponse checkInIntroResponse = new CheckInIntroResponse(checkIn);
            List<String> imageUrls = checkInMapper.getUrlByCheckInId(checkIn.getCheck_in_id());
            checkInIntroResponse.setImage_urls(imageUrls);
            
            checkInIntroResponses.add(checkInIntroResponse);
        }
        
        return checkInIntroResponses;
    }
}
