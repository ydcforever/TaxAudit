package com.atpco.audit.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by T440 on 2018/4/26.
 */
public class BaseController {

    public static void writeJson(int total, String json, HttpServletResponse response) {
        String content = makeJson(total, json);
        writeJson(content, response);
    }

    /**
     *
     * @param total
     * @param json
     * @return
     */
    public static String makeJson(int total, String json) {
        String newJson = "{\"total\":" + total + " , \"rows\":" + json + "}";
        return newJson;
    }

    /**
     *
     * @param json
     * @param response
     */
    public static void writeJson(String json, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter writer = null;
        try{
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
        }catch (Exception e){

        }finally {
            if(writer!=null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
