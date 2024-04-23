package com.hxt.backend.service;

import com.hxt.backend.entity.section.Section;
import com.hxt.backend.mapper.SectionMapper;
import com.hxt.backend.response.sectionResponse.SectionElement;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    public ArrayList<SectionElement> getHotSections() {
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


    public ArrayList<SectionElement> searchSection(String keyWord, Integer sort, Integer type, String academy) {
        ArrayList<Section> sections = sectionMapper.selectSectionByName(keyWord);
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
        return false;
    }

    public boolean unfocusSection(Integer userId, Integer sectionId) {
        return false;
    }

}
