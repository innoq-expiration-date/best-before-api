package ch.vshn.hackvision.processing;

import java.util.ArrayList;
import java.util.List;

public class DateExtractionResult {
    public String errorMessage;
    public boolean candidateFound;
    public String textContents;
    public List<ExtractedDate> extractedDates = new ArrayList<>();
}