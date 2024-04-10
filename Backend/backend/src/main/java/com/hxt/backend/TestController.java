package com.hxt.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/test")
    @ResponseBody
    public String hello() {
        String sql = "select * from test";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list.get(0).get("info").toString();
    }
}
