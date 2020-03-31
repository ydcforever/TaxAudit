package com.atpco.audit.controller;

import com.atpco.audit.util.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ydc on 2019/10/29.
 */
@Controller
@RequestMapping(value = "/restful")
public class RestfulController extends BaseController {

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        String queryCondition = request.getParameter("queryCondition");
        HashMap<String, Object> map = new HashMap<>();
        map = JsonUtil.getBean(queryCondition, map);
        String body = javaTax3(map);
        String result = query("http://172.28.98.28:9080/tax_engine/v3/api/taxyq", body);
        if (!result.equals("")) {
            writeJson(result, response);
        }
    }

    @RequestMapping(value = "/pro3.do")
    public void queryTax3(HttpServletRequest request, HttpServletResponse response) {
        String queryCondition = request.getParameter("queryCondition");
        HashMap<String, Object> map = new HashMap<>();
        map = JsonUtil.getBean(queryCondition, map);
        String body = procedureTax3(map);
        String result = query("http://172.28.98.23:9080/enclosure/tax3/api/taxyq", body);
        if (!result.equals("")) {
            writeJson(result, response);
        }
    }

    public String query(String uri, String requestJson) {
        HttpGetWithEntity e = new HttpGetWithEntity();
        e.setURI(URI.create(uri));
        e.addHeader("Content-Type", "application/json");
        e.addHeader("Accept", "application/json");
        ByteArrayEntity entity = new ByteArrayEntity(requestJson.getBytes(StandardCharsets.UTF_8));
        entity.setContentType("application/json");
        e.setEntity(entity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse res = httpClient.execute(e)) {
            HttpEntity responseEntity = res.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private static class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
        private final static String METHOD_NAME = "GET";

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }

    public String javaTax3(Map<String, Object> map) {
        StringBuilder body = new StringBuilder();
        String[] seg = map.get("routing").toString().split(";");
        String[] mkt = map.get("marketing_carrier").toString().split(";");
        String[] cls = map.get("clazz").toString().split(";");
        String[] dt = map.get("departure_date").toString().split(";");
        String[] at = map.get("arrival_date").toString().split(";");
        String[] ft = map.get("flights").toString().split(";");
        String[] pt = map.get("plane_types").toString().split(";");
        body.append("{\"scurr\":\"").append(map.get("sale_currency"))
                .append("\",\"passenger\":{\"type\":\"").append(map.get("passenger_type"))
                .append("\"},\"sectors\":[");

        for (int i = 0, len = seg.length; i < len; i++) {
            body.append("{")
                    .append("\"clazz\":\"").append(cls[i])
                    .append("\",\"begin\":\"").append(dt[i])
                    .append("\",\"end\":\"").append(at[i])
                    .append("\",\"from\":\"").append(seg[i].substring(0, 3))
                    .append("\",\"to\":\"").append(seg[i].substring(4, 7))
                    .append("\",\"mcxr\":\"").append(mkt[i]);
            if (!"".equals(ft[0])) {
                body.append("\",\"mfno\":\"").append(mkt[i]).append(ft[i]);
            }
            if (!"".equals(pt[0])) {
                body.append("\",\"ptype\":\"").append(pt[i]);
            }
            body.append("\"}");
            if (i < len - 1) {
                body.append(",");
            }
        }
        body.append("]}");
        return body.toString();
    }

    public String procedureTax3(Map<String, Object> map) {
        StringBuilder body = new StringBuilder();
        String flt = map.get("flights").toString();
        String mkt = map.get("marketing_carrier").toString();
        String opt = map.get("operating_carrier").toString();

        String t_mkt = "";
        String t_opt = "";
        if (!"".equals(flt)) {
            String[] flt_nos = flt.split(";");
            String[] mkts = mkt.split(";");
            String[] opts = opt.split(";");
            for (int i = 0, len = flt_nos.length; i < len; i++) {
                t_mkt += mkts[i] + flt_nos[i] + ";";
                t_opt += opts[i] + flt_nos[i] + ";";
            }
        } else {
            t_opt = opt;
            t_mkt = mkt;
        }
        String id = "N";
        String region = "CN";
        body.append("{\"bdate\":\"").append(map.get("booking_date")).append("\",\"lang\":\"ZH\",\"resultType\":\"T\",\"routeList\":[{\"begin\":\"")
                .append(map.get("departure_date")).append("\",\"end\":\"").append(map.get("arrival_date")).append("\",\"clazz\":\"").append(map.get("clazz"))
                .append("\",\"mcxr\":\"").append(t_mkt).append("\",\"ocxr\":\"").append(t_opt).append("\",\"passenger\":[{")
                .append("\"type\":\"").append(map.get("passenger_type"))
                //.append("\,"age\":").append(map.get("passenger_age"))
                .append("\",\"fee\":{\"fare\":\"").append(map.get("fare_amount")).append("\"},\"id\":\"").append(id)
                .append("\",\"region\":\"").append(region)
                .append("\"}],\"routing\":\"")
                .append(map.get("routing")).append("\"}],")
                .append("\"scurrency\":\"").append(map.get("sale_currency")).append("\",\"spoint\":\"").append(map.get("sale_point"))
                .append("\",\"tcxr\":\"").append(map.get("sale_organization")).append("\",\"tpoint\":\"").append(map.get("ticket_point"))
                .append("\",\"user\":{\"bookingChannelCode\":\"13\",\"bookingChannelName\":\"B2CUS\",")
                .append("\"encryptedStr\":\"FDDD31DF3B9C24A8D16EFA23A216C1039E2577D1D0160A3F626B6ED93B984CEBCFC1C6C916936C87\"")
                .append("}}");
        return body.toString();
    }
}
