package com.pol.calendarinterceptor.calendarinterceptor.interceptors;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Qualifier("calendarInterceptor")
public class CalendarInterceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private Integer open;
    @Value("${config.calendar.opcloseen}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < open || hour > close) {
            response.getWriter().write("The office is closed");
            response.setStatus(403);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("message", "The office is closed");
            data.put("timestamp", System.currentTimeMillis());
            data.put("status", 403);
            data.put("openHour", open);
            data.put("closeHour", close);
            response.getWriter().write(mapper.writeValueAsString(data));
            response.setStatus(403);

            return false;
        }

        StringBuilder message = new StringBuilder("The office is open, current time is: ");
        message.append(hour);
        message.append(". We open at ");
        message.append(open);
        message.append("h. and close at ");
        message.append(close);
        message.append(" h.");

        request.setAttribute("message", message.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

}
