package ch.vshn.hackvision.service;


import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.RegExUtils;

import java.util.Collection;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class RegexDateParser implements DateParser {

    private static Pattern DATE_PATTERN_1 = Pattern.compile(
            "(0?[1-9]|[12]\\d|30|31)[^\\w\\d\\r\\n:](0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](\\d{4})");

//    private static Pattern DATE_PATTERN_2 = Pattern.compile(
//            "(\\d{4}|\\d{2})[^\\w\\d\\r\\n:](0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](0?[1-9]|[12]\\d|30|31)");

    private static Pattern DATE_PATTERN_3 = Pattern.compile(
            "(0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](\\d{4})");

//    private static Pattern DATE_PATTERN_4 = Pattern.compile(
//            "(\\d{4}|\\d{2})[^\\w\\d\\r\\n:](0?[1-9]|1[0-2])");

    @Override
    public List<String> parse(String ocrResult) {

        List<String> result1 = parsePattern(ocrResult, DATE_PATTERN_1);
        String reducedOcr = RegExUtils.removeAll(ocrResult, DATE_PATTERN_1);

//        List<String> result2 = parsePattern(reducedOcr, DATE_PATTERN_2);
//        reducedOcr =RegExUtils.removeAll(ocrResult, DATE_PATTERN_2);

        List<String> result3 = parsePattern(reducedOcr, DATE_PATTERN_3);
        reducedOcr =RegExUtils.removeAll(ocrResult, DATE_PATTERN_3);

//        List<String> result4 = parsePattern(reducedOcr, DATE_PATTERN_4);
//        reducedOcr =RegExUtils.removeAll(ocrResult, DATE_PATTERN_4);

        return Stream.of(result1, result3)
                .flatMap(Collection::stream)
                .filter(this::isValidYear)
                .collect(Collectors.toList());
    }



    private List<String> parsePattern(String ocrResult, Pattern pattern){

        Matcher matcher = pattern.matcher(ocrResult);

        List<MatchResult> matchResults = matcher.results().collect(Collectors.toList());

        return matchResults.stream()
                .map(matchResult -> ocrResult.substring(matchResult.start(), matchResult.end()).trim())
                .collect(Collectors.toList());
    }

    private boolean isValidYear(String date){

        String yearString = date.substring(date.length() - 4);
        int year = Integer.parseInt(yearString);
        return year >= 2020 && year <=2030;
    }


}
