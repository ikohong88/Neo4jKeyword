package com.example.neo4jkeyword.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Iterator;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestKeywordDto {
    // 키워드
    private String keyword;
    // 연관키워드 및 가중치
    private HashMap<String,Integer> keywords;

    public Integer getWeight(String relatedKeyword) {
        return keywords.get(relatedKeyword);
    }
    public Iterator<String> getRelatedKeyword() {
        return keywords.keySet().iterator();
    }
}
