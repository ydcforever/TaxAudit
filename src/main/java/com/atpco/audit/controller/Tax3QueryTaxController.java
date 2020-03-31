package com.atpco.audit.controller;

import com.atpco.audit.mapper.tax3.QueryTaxMapper;
import com.atpco.audit.model.QueryTax;
import com.atpco.audit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ydc on 2019/5/30.
 */
@Controller
@RequestMapping(value = "/tax3")
public class Tax3QueryTaxController extends BaseController {
    @Autowired
    private QueryTaxMapper queryTaxMapper;

    private static final DecimalFormat DF = new DecimalFormat("#0.00");

    @RequestMapping(value = "/queryTax.do")
    public void queryTax(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // TODO Auto-generated constructor stub
        String queryCondition = request.getParameter("queryCondition");
        HashMap<String, Object> map = new HashMap<>();
        map = JsonUtil.getBean(queryCondition, map);
        queryTaxMapper.queryTax(map);
        Object routing_yq_amount = map.get("routing_yq_amount");
        BigDecimal yq = BigDecimal.ZERO;
        if (routing_yq_amount != null) {
            yq = new BigDecimal(DF.format(routing_yq_amount));
        }

        BigDecimal yr = BigDecimal.ZERO;
        Object routing_yr_amount =  map.get("routing_yr_amount");
        if (routing_yr_amount != null) {
            yr = new BigDecimal(DF.format(routing_yr_amount));
        }
        List<QueryTax> result = new ArrayList<>();
        try {
            result = (List<QueryTax>) map.get("result_recs");
            if ( !(result.get(0) instanceof QueryTax)) {
                result.clear();
            }
            result.add(new QueryTax(map.get("sale_currency").toString(), "YQ", yq.add(yr).doubleValue()));
        }catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtil.list2json(result);
        writeJson(json, response);
    }

    public HashMap create(HashMap hashMap) {
        HashMap map = new HashMap();
        map.put("routing",  hashMap.get("routing").toString());
        map.put("turnaround_no", hashMap.get("turnaround_no").toString());
        map.put("booking_date", hashMap.get("booking_date").toString());
        map.put("marketing_carrier", hashMap.get("marketing_carrier").toString());
        map.put("operating_carrier", hashMap.get("operating_carrier").toString());
        map.put("flights", hashMap.get("flightNumber").toString());
        map.put("plane_types", hashMap.get("planeType").toString());
        map.put("sale_organization", hashMap.get("sale_organization").toString());
        map.put("clazz", hashMap.get("clazz").toString());
        String passengerType = hashMap.get("passenger_type").toString();
        map.put("passenger_type", passengerType);
        String passengerAge = hashMap.get("passenger_age").toString();
        if (StringUtils.isBlank(passengerAge)) {
            int pass =  StringUtils.isBlank(passengerType) || "ADT".equals(passengerType) ? 18:
                    "CHD".equals(passengerType) ? 10 : 1;
            map.put("passenger_age", pass);
        } else {
            map.put("passenger_age", Integer.parseInt(passengerAge));
        }
        map.put("passenge_identity", hashMap.get("passenge_identity").toString());
        map.put("passenger_region_type", hashMap.get("passenger_region_type").toString());
        map.put("passenger_region", hashMap.get("passenger_region").toString());
        map.put("sale_point", hashMap.get("sale_point").toString());
        map.put("ticket_point", hashMap.get("ticket_point").toString());
        map.put("sale_currency", hashMap.get("sale_currency").toString());
        map.put("fare_base","");

        String fareAmount = hashMap.get("fare_amount").toString();
        if (StringUtils.isBlank(fareAmount)) {
            map.put("fare_amount", 0);
        } else {
            map.put("fare_amount", new Double(fareAmount));
        }

        String obFee = hashMap.get("ob_fee").toString();
        if (StringUtils.isBlank(obFee)) {
            map.put("ob_fee", 0);
        } else {
            map.put("ob_fee", new Double(obFee));
        }

        String servicesFlightRelatedFee = hashMap.get("services_flight_related_fee").toString();
        if (StringUtils.isBlank(servicesFlightRelatedFee)) {
            map.put("services_flight_related_fee", 0);
        } else {
            map.put("services_flight_related_fee", new Double(servicesFlightRelatedFee));
        }

        String servicesTicketRelatedFee = hashMap.get("services_ticket_related_fee").toString();
        if (StringUtils.isBlank(servicesTicketRelatedFee)) {
            map.put("services_ticket_related_fee", 0);
        } else {
            map.put("services_ticket_related_fee", new Double(servicesTicketRelatedFee));
        }

        String servicesMerchandiseFee = hashMap.get("services_merchandise_fee").toString();
        if (StringUtils.isBlank(servicesMerchandiseFee)) {
            map.put("services_merchandise_fee", 0);
        } else {
            map.put("services_merchandise_fee", new Double(servicesMerchandiseFee));
        }

        String servicesOcFee = hashMap.get("services_oc_fee").toString();
        if (StringUtils.isBlank(servicesOcFee)) {
            map.put("services_oc_fee", 0);
        } else {
            map.put("services_oc_fee", new Double(servicesOcFee));
        }

        String baggageCharges = hashMap.get("baggage_charges").toString();
        if (StringUtils.isBlank(baggageCharges)) {
            map.put("baggage_charges", 0);
        } else {
            map.put("baggage_charges", new Double(baggageCharges));
        }

        String odFee = hashMap.get("od_fee").toString();
        if (StringUtils.isBlank(odFee)) {
            map.put("od_fee", 0);
        } else {
            map.put("od_fee", new Double(odFee));
        }

        map.put("departure_date", hashMap.get("departure_date").toString());
        map.put("arrival_date", hashMap.get("arrival_date").toString());
        return map;
    }
}
