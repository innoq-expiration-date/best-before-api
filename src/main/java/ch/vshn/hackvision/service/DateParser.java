package ch.vshn.hackvision.service;

import java.util.List;

public interface DateParser {
    List<String> parse(String ocrResult);
}
