package com.example.neo4jkeyword.DataRunner;

import com.example.neo4jkeyword.Neo4jKeywordApplication;
import com.example.neo4jkeyword.Node.Keyword;
import com.example.neo4jkeyword.Repository.RelatedKeywordRepository;
import com.example.neo4jkeyword.Service.RelatedKeywordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataRunner implements ApplicationRunner {
    private final RelatedKeywordRepository relatedKeywordRepository;
    private final RelatedKeywordService relatedKeywordService;
    private final static Logger log = LoggerFactory.getLogger(Neo4jKeywordApplication.class);

    public void run(ApplicationArguments args) throws Exception {
        relatedKeywordRepository.deleteAll();

        JSONParser parser = new JSONParser();
        // JSON 파일 읽기
        Reader reader = new FileReader("C:\\Users\\akoho\\Desktop\\DGtimes_Python\\DG-times-python\\neo4jNewsKeywordLimit10.json");
        JSONArray dateArray = (JSONArray) parser.parse(reader);

        readJSON(dateArray);
    };

    public void setRelatedKeyword(List<String> keywords) {
        for (int a = 0; a < keywords.size()-1; a++) {
            String pickKeyword = keywords.get(a);
            for (int b = a+1; b < keywords.size(); b++) {
                relatedKeywordService.searchRelatedValue(pickKeyword,keywords.get(b));
            }
        }
    }

    @Async("threadPoolTaskExecutor")
    public void readJSON(JSONArray dateArray) throws Exception {
        int count = 0;
        JSONObject element;

        for (int i = 0; i < dateArray.size(); i++) {
            count++;
            System.out.println("Start");
            System.out.println("count = " + count);

            element = (JSONObject) dateArray.get(i);
            JSONObject keywordObj = (JSONObject) element.get("keyword");
            Map<String, Integer> newsKeyword = new HashMap<>();
            newsKeyword = new ObjectMapper().readValue(keywordObj.toString(), Map.class);

            Iterator<String> keys = newsKeyword.keySet().iterator();
            List<String> keywords = new ArrayList<>();
            while (keys.hasNext()){
                String key = keys.next();
                relatedKeywordService.searchValue(key);
                keywords.add(key);
            }
            setRelatedKeyword(keywords);
        }
    }
}

