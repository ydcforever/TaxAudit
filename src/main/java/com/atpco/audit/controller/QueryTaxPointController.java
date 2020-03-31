package com.atpco.audit.controller;

import com.atpco.audit.mapper.tax3.QueryTaxPointMapper;
import com.atpco.audit.model.AtpcoTTBSX1;
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
 * Created by ydc on 2019/5/31.
 */
@Controller
@RequestMapping(value = "/tax3")
public class QueryTaxPointController extends BaseController{
    @Autowired
    private QueryTaxPointMapper queryTaxPointMapper;

    @RequestMapping(value = "/queryTaxPoint.do")
    public void queryTaxPoint(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        String queryCondition = request.getParameter("queryCondition");
        try {
            queryCondition = URLDecoder.decode(queryCondition, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map = JsonUtil.getBean(queryCondition, map);
        queryTaxPointMapper.queryTaxPoint(map);
        List<AtpcoTTBSX1> list = new ArrayList<>();
        try {
            list = ( List<AtpcoTTBSX1>) map.get("result_recs");
        }catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
