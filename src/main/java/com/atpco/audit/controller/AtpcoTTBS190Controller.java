package com.atpco.audit.controller;

import com.atpco.audit.mapper.share.AtpcoTTBS190Mapper;
import com.atpco.audit.model.AtpcoTTBS190;
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
@RequestMapping(value = "/atpco/ttbs/190")
public class AtpcoTTBS190Controller extends BaseController {
    @Autowired
    private AtpcoTTBS190Mapper atpcoTTBS190Mapper;

    @RequestMapping(value = "/query.do")
    public void query190(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        String customNo = request.getParameter("customNo");
        List<AtpcoTTBS190> list = atpcoTTBS190Mapper.query190(customNo);
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
