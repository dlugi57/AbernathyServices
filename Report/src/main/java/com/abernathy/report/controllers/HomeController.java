package com.abernathy.report.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class HomeController {

    static final Logger logger = LogManager
            .getLogger(HomeController.class);

    /**
     * Home page
     *
     * @return home page
     */
    @RequestMapping("/")
    public String home() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        logger.info(("You are connected at "
                + format.format(calendar.getTime())));
        return "home";
    }


}
