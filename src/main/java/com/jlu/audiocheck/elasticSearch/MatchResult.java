package com.jlu.audiocheck.elasticSearch;

import lombok.Data;

import java.util.HashMap;

@Data
public class MatchResult {
    private HashMap<String, MatchData> data = new HashMap<>();
}
