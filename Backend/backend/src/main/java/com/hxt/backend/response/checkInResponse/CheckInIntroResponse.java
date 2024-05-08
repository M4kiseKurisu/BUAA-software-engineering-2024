package com.hxt.backend.response.checkInResponse;

import com.hxt.backend.entity.checkIn.CheckIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CheckInIntroResponse {
    private Integer post_id;
    private Integer year;
    private Integer month;
    private List<String> image_urls;
    
    public CheckInIntroResponse(CheckIn checkIn) {
        this.post_id = checkIn.getCheck_in_id();
        this.year = getYear(checkIn.getTime());
        this.month = getMonth(checkIn.getTime());
    }
    
    
    
    public static Integer getYear(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.getYear();
    }
    
    public static Integer getMonth(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.getMonthValue();
    }
}
