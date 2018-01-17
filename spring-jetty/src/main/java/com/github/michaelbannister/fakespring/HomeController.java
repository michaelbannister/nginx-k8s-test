package com.github.michaelbannister.fakespring;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class HomeController {

    @Value("${delay.min.millis:0}")
    private long minDelayMillis;
    @Value("${delay.max.millis:0}")
    private long maxDelayMillis;

    private Random random = new Random();

    @GetMapping("/")
    public String home() {
        if (maxDelayMillis != 0) {
            sleep(RandomUtils.nextLong(minDelayMillis, maxDelayMillis));
        }

        return "index";
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
