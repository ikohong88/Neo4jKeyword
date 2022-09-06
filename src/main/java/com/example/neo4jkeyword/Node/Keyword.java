package com.example.neo4jkeyword.Node;

import com.example.neo4jkeyword.Dto.RequestKeywordDto;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Node
@Getter
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;

    private String value;

    private Integer weight;

    private Object relatedValue;

    // 없으면 안됨
    private Keyword() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", weight='" + weight + '\'' +
                ", relatedValue=" + relatedValue +
                '}';
    }
//    뉴스에서 가져온 연관키워드
//    검색에서 가져온 연관키워드
    
//    @Relationship(type = "related")
//    public Set<Keyword> related;
//
//    public void worksWith(Keyword keyword) {
//        if (related == null) {
//            related = new HashSet<>();
//        }
//        System.out.println("keyword = " + keyword);
//        related.add(keyword);
//    }
}
