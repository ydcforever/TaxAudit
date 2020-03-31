package com.atpco.audit.controller;

import com.atpco.audit.mapper.share.CurrencyRegulationMapper;
import com.atpco.audit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ydc on 2019/5/30.
 */
@Controller
@RequestMapping(value = "/currency")
public class CurrencyRegulationController extends BaseController {
    @Autowired
    private CurrencyRegulationMapper currencyRegulationMapper;

    @RequestMapping(value = "/queryCurrency.do")
    public void queryCurrency(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        List<String> list = currencyRegulationMapper.queryCurrency();
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }
}
