package com.atpco.audit.controller;

import com.atpco.audit.mapper.tax3.RoutingTestMapper;
import com.atpco.audit.model.RoutingTest;
import com.atpco.audit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ydc on 2019/8/14.
 */
@Controller
@RequestMapping(value = "/test")
public class RoutingTestController extends BaseController {
    @Autowired
    private RoutingTestMapper routingTestMapper;

    private static final DecimalFormat DF = new DecimalFormat("#0.00");

    @RequestMapping(value = "/queryRouting.do")
    public void queryAreaPartition(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        String queryCondition = request.getParameter("queryCondition");
        try {
            queryCondition = URLDecoder.decode(queryCondition, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map = JsonUtil.getBean(queryCondition, map);
        String diffLow = map.get("diffLow").toString();
        String diffUp = map.get("diffUp").toString();
        int taxAmount = Integer.parseInt(map.get("taxAmount").toString());

        List<RoutingTest> list = routingTestMapper.query(taxAmount, diffLow, diffUp);
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
