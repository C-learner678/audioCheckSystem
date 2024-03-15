package com.jlu.audiocheck.elasticSearch;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class MatchData {
    private String name;
    private HashMap<String, String> patternToHighlightedText;

    public MatchData(String name, HashMap<String, String> patternToHighlightedText){
        this.name = name;
        this.patternToHighlightedText = patternToHighlightedText;
    }
}
