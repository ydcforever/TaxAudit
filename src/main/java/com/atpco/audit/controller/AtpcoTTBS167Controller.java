package com.atpco.audit.controller;

import com.atpco.audit.mapper.share.AtpcoTTBS167Mapper;
import com.atpco.audit.model.AtpcoTTBS167;
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
@RequestMapping(value = "/atpco/ttbs/167")
public class AtpcoTTBS167Controller extends BaseController {
    @Autowired
    private AtpcoTTBS167Mapper atpcoTTBS167Mapper;

    @RequestMapping(value = "/query.do")
    public void query167(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        String customNo = request.getParameter("customNo");
        List<AtpcoTTBS167> list = atpcoTTBS167Mapper.query167(customNo);
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
