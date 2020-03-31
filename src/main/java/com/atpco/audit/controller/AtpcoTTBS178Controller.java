package com.atpco.audit.controller;

import com.atpco.audit.mapper.share.AtpcoTTBS178Mapper;
import com.atpco.audit.model.AtpcoTTBS178;
import com.atpco.audit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ydc on 2019/6/1.
 */
@Controller
@RequestMapping(value = "/atpco/ttbs/178")
public class AtpcoTTBS178Controller extends BaseController {

    @Autowired
    private AtpcoTTBS178Mapper atpcoTTBS178Mapper;

    @RequestMapping(value = "/query.do")
    public void query178(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        String customNo = request.getParameter("customNo");
        List<AtpcoTTBS178> list = atpcoTTBS178Mapper.query178(customNo);
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
