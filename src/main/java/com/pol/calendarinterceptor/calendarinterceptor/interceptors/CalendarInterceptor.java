package com.pol.calendarinterceptor.calendarinterceptor.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component()
public class CalendarInterceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private Integer open;
    @Value("${config.calendar.close}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < open || hour > close) {

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> data = new HashMap<>();
            StringBuilder message = new StringBuilder("The office is closed, current hour is ");
            message.append(hour);
            message.append("h, we are open from ");
            message.append(open);
            message.append("h to ");
            message.append(close);
            message.append("h");

            //
            data.put("message", message.toString());
            data.put("date", new Date().toString());

            response.setContentType("application/json");
            response.setStatus(401);

            response.getWriter().write(mapper.writeValueAsString(data));

            return false;
        }

        StringBuilder message = new StringBuilder();
        message.append("The office is open, ");
        message.append("current hour is ");
        message.append(hour);
        message.append("h, we are open from ");
        message.append(open);
        message.append("h to ");
        message.append(close);
        message.append("h");

        request.setAttribute("message", message);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

}
