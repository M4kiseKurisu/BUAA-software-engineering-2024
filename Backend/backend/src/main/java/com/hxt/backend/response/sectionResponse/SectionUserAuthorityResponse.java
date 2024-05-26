package com.hxt.backend.response.sectionResponse;

import com.hxt.backend.response.BasicInfoResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionUserAuthorityResponse extends BasicInfoResponse {
    private boolean blocked;

    public SectionUserAuthorityResponse(boolean success, String info, boolean blocked) {
        super(success, info);
        this.blocked = blocked;
    }
}
