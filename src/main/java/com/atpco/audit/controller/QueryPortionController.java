package com.atpco.audit.controller;

import com.atpco.audit.mapper.tax3.QueryPortionMapper;
import com.atpco.audit.model.AtpcoYQYRS1;
import com.atpco.audit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ydc on 2019/6/2.
 */
@Controller
@RequestMapping(value = "/tax3")
public class QueryPortionController extends BaseController{
    @Autowired
    private QueryPortionMapper queryPortionMapper;

    @RequestMapping(value = "/queryPortion.do")
    public void queryPortion(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        HashMap<String, Object> map = new HashMap<>();
        String queryCondition = request.getParameter("queryCondition");
        try {
            queryCondition = URLDecoder.decode(queryCondition, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map = JsonUtil.getBean(queryCondition, map);
        queryPortionMapper.queryPortion(map);
        List<AtpcoYQYRS1> list = new ArrayList<>();
        try {
            list = ( List<AtpcoYQYRS1>) map.get("result_recs");
        }catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
