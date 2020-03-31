package com.atpco.audit.controller;

import com.atpco.audit.util.JsonUtil;
import com.btw.webservice.coupon.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ydc on 2019/9/19.
 */
@Controller
@RequestMapping(value = "/tax2Ex")
public class Tax2ExchangeQueryController extends BaseController {
    @RequestMapping(value = "/queryTax.do")
    public void queryTax(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // TODO Auto-generated constructor stub
        String queryCondition = request.getParameter("queryCondition");
        HashMap<String, Object> map = new HashMap<>();
        map = JsonUtil.getBean(queryCondition, map);
        List<Tax> taxes = query(map);
        String json = JsonUtil.list2json(taxes);
        writeJson(json, response);
    }

    public List<Tax> query(Map map) throws Exception{
        B2XTaxYQ b2XTaxYQ = createB2XTaxYQ(map);
        String key = "0291616183F9D024E63F699FEB213E664A2EE822A4E374399A87A5921E5E7A63834CD3537441C54A";
        IBTWTaxYQCouponQuery query = (new BTWTaxYQCouponQueryImplService()).getBTWTaxYQCouponQueryImplPort();
        B2XTaxYQ result = query.btwTaxYQCouponQuery(key, b2XTaxYQ);
        return result.getPfList().get(0).getTaxList();
    }

    public B2XTaxYQ createB2XTaxYQ(Map map) {
        B2XTaxYQ b2XTaxYQ = new B2XTaxYQ();
        b2XTaxYQ.setSalesCountry("CN");
        b2XTaxYQ.setTicketingDate(map.get("booking_date").toString());
        b2XTaxYQ.setSalesCurrency(map.get("sale_currency").toString());
        PsgFare psgFare = new PsgFare();
        psgFare.setFare(map.get("fare_amount").toString());
        psgFare.setPsgType(map.get("passenger_type").toString());
        b2XTaxYQ.getPfList().add(psgFare);

        String[] airports = map.get("routing").toString().split(";");
        String clazz = map.get("clazz").toString();
        String[] clazzes = clazz.split(";");

        String dt = map.get("departure_date").toString();
        String at = map.get("arrival_date").toString();
        String[] dts = new String[0];
        String[] ats = new String[0];
        if(!StringUtils.isBlank(dt)) {
            dts = dt.split(";");
        }
        if(!StringUtils.isBlank(at)) {
            ats = at.split(";");
        }

        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < airports.length; i++) {
            Segment segment = new Segment();
            segment.setClazz(StringUtils.isBlank(clazz) ? "Y" : clazzes[i]);
            segment.setOrgcd(airports[i].substring(0, 3));
            segment.setDestcd(airports[i].substring(4, 7));
            if (dts.length > 0) {
                segment.setOrgdt(dts[i]);
            }
            if (ats.length > 0) {
                segment.setDestdt(ats[i]);
            }
            segments.add(segment);
        }
        b2XTaxYQ.getSegList().addAll(segments);
        return b2XTaxYQ;
    }
}
