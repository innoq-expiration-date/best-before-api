package ch.vshn.hackvision.service;

import org.junit.jupiter.api.Assertions;

import java.util.List;


class DateParserServiceTest {



    @org.junit.jupiter.api.Test
    void parse() {
        String testString = "ffff 11/11/1111 hhh 12.2025 jhhh 31.11.2022";

        RegexDateParser dateParserService = new RegexDateParser();
        List<String> dates = dateParserService.parse(testString);

        dates.forEach(string -> System.out.println(string));

        Assertions.assertFalse(dates.isEmpty());
    }
}
