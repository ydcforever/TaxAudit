package com.atpco.audit.controller;

import com.atpco.audit.util.JsonUtil;
import com.ceair.muibe.provider.MBEConstant;
import com.ceair.muibe.provider.UserInfo;
import com.ceair.muibe.provider.pricing.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ydc on 2019/6/3.
 */
@Controller
@RequestMapping(value = "/HP")
public class HPController extends BaseController {

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        UserInfo userInfo = new UserInfo();
        userInfo.setChannelId("FARE");
        userInfo.setChannelPwd("FARE");
        userInfo.setUserId("HAIWAI");

        String queryCondition = request.getParameter("queryCondition");
        HashMap<String, Object> map = new HashMap<>();
        map = JsonUtil.getBean(queryCondition, map);

        PricingReq pricingReq = new PricingReq();
        pricingReq.setUser(userInfo);
        pricingReq.setTicketingCarrier(map.get("ticketingCarrier").toString());
        pricingReq.setCurrencyCode(map.get("sale_currency").toString());

        /** 添加行程信息 */
        String[] routing = split(map, "routing");
        String[] flightNumber = split(map, "flights");
        String[] planeType = split(map, "plane_types");
        String[] operatingCarrier = split(map, "operating_carrier");
        String[] departureDate = split(map, "departure_date");
        String[] arrivalDate = split(map, "arrival_date");
        String[] clazz = split(map, "clazz");
        String[] carrier = split(map, "carrier");
        List<PiFlightSegment> piFlightSegments = new ArrayList<>();
        for (int i = 1, cnt = routing.length; i <= cnt; i++) {
            PiFlightSegment piFlightSegment = new PiFlightSegment();
            piFlightSegment.setSegIndex(i);
            if (flightNumber != null) {
                piFlightSegment.setFlightNumber(flightNumber[i - 1]);
            }
            if (clazz == null) {
                piFlightSegment.setCabin("Y");
            } else {
                piFlightSegment.setCabin(clazz[i - 1]);
            }
            String[] airports = routing[i - 1].split("-");
            piFlightSegment.setOrgCode(airports[0]);
            piFlightSegment.setDestCode(airports[1]);

            if (operatingCarrier == null) {
                piFlightSegment.setOperatingCarrier("MU");
            } else {
                piFlightSegment.setOperatingCarrier(operatingCarrier[i - 1]);
            }

            if (carrier == null) {
                piFlightSegment.setCarrier("MU");
            } else {
                piFlightSegment.setCarrier(carrier[i - 1]);
            }

            if (departureDate != null) {
                piFlightSegment.setDepartureDateTime(getCalendar(departureDate[i - 1]).getTime());
            }

            if (arrivalDate != null) {
                piFlightSegment.setArrivalDateTime(getCalendar(arrivalDate[i - 1]).getTime());
            }

            if (planeType != null) {
                piFlightSegment.setPlanType(planeType[i - 1]);
            }
            piFlightSegments.add(piFlightSegment);
        }
        pricingReq.setPiFlightSegments(piFlightSegments);

        /** 添加成人旅客 */
        List<PiPassenger> piPassengerList = new ArrayList<>();
        PiPassenger piPassenger = new PiPassenger();
        String passengerType = map.get("passenger_type").toString();
        if ("CHD".equals(passengerType)) {
            piPassenger.setPassengerType(MBEConstant.PASSENGER_TYPE_CHD);
        } else if ("INF".equals(passengerType)) {
            piPassenger.setPassengerType(MBEConstant.PASSENGER_TYPE_INF);
        } else {
            piPassenger.setPassengerType(MBEConstant.PASSENGER_TYPE_ADT);
        }
        piPassenger.setPassengerNumber(1);
        piPassengerList.add(piPassenger);
        pricingReq.setPiPassengers(piPassengerList);
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:dubbo_pricing.xml");

        OutPricingService outPricingService = (OutPricingService) ac.getBean("outPricingService");
        List<PiTaxBreakDown> list = new ArrayList<>();
        try {
            PricingRes pricingRes = outPricingService.pricing(pricingReq);
            pricingRes.getResponseCode();
            int sizeone = pricingRes.getPiPricedItineraryList().size();
            //request.setAttribute("PiPricedItineraryListSizeone",sizeone);
            if (sizeone > 0) {
                int sizetwo = pricingRes.getPiPricedItineraryList().get(0).getFareBreakDowns().size();
                //request.setAttribute("FareBreakDownsSizetwo",sizetwo);
                if (sizetwo > 0) {
                    list = pricingRes.getPiPricedItineraryList().get(0).getFareBreakDowns().get(0).getTaxBreakDowns();
                }
            }
        } catch (Exception e) {

        }
        String json = JsonUtil.list2json(list);
        writeJson(json, response);
    }

    public Calendar getCalendar(String date) {
        if ("".equals(date)) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)),
                    Integer.parseInt(date.substring(8, 10)),
                    Integer.parseInt(date.substring(11, 13)),
                    Integer.parseInt(date.substring(14, 16)));
            return calendar;
        }
    }

    public String[] split(HashMap<String, Object> map, String key) {
        String val = map.get(key).toString();
        if ("".equals(val.trim())) {
            return null;
        } else {
            return val.split(";");
        }
    }
}
