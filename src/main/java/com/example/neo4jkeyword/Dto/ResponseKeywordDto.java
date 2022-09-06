package com.example.neo4jkeyword.Dto;

import com.example.neo4jkeyword.Node.Keyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseKeywordDto {
    // 키워드
    private String keyword;
    // 연관키워드 및 가중치
    private HashMap<String,Integer> keywords;

    public ResponseKeywordDto(String keyword, List<Keyword> relatedKeywords) {
        this.keyword = keyword;
        this.keywords = new HashMap<>();
        for (int i = 0; i < relatedKeywords.size(); i++) {
            String value = relatedKeywords.get(i).getValue();
            Integer weight = relatedKeywords.get(i).getWeight();
            this.keywords.put(value,weight);
        }
    }
}
