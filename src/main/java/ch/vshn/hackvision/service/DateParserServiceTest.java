package ch.vshn.hackvision.service;

import org.junit.jupiter.api.Assertions;

import java.util.List;


class DateParserServiceTest {



    @org.junit.jupiter.api.Test
    void parse() {
        String testString = "ffff 11/11/11 ffff 22.12 jhhh 31.11.2012";

        RegexDateParser dateParserService = new RegexDateParser();
        List<String> dates = dateParserService.parse(testString);

        dates.forEach(string -> System.out.println(string));

        Assertions.assertFalse(dates.isEmpty());
    }
}
