package com.atpco.audit.controller;

import com.atpco.audit.mapper.share.AreaPartitionMapper;
import com.atpco.audit.model.AreaPartition;
import com.atpco.audit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by T440 on 2018/4/26.
 */
@Controller
@RequestMapping(value = "/airport")
public class AreaPartitionController extends BaseController {

    @Autowired
    private AreaPartitionMapper areaPartitionMapper;

    @RequestMapping(value = "/queryAreaPartition.do")
    public void queryAreaPartition(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        String airport = request.getParameter("airport");
        AreaPartition list = areaPartitionMapper.queryAreaPartition(airport);
        String json = JsonUtil.bean2json(list);
        writeJson(json, response);
    }

    @RequestMapping(value = "/queryAreaPartitions.do")
    public void queryAreaPartitions(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        ArrayList<String> airports = new ArrayList<>();
        JsonUtil.getList(request.getParameter("airports"), airports);
        List<AreaPartition> list = areaPartitionMapper.queryAreaPartitions(airports);
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
