package com.example.neo4jkeyword.Controller;

import com.example.neo4jkeyword.Dto.DefaultResponseDto;
import com.example.neo4jkeyword.Dto.RequestKeywordDto;
import com.example.neo4jkeyword.Dto.ResponseKeywordDto;
import com.example.neo4jkeyword.Node.Keyword;
import com.example.neo4jkeyword.Repository.RelatedKeywordRepository;
import com.example.neo4jkeyword.Service.RelatedKeywordService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RelatedKeywordController {
    private final RelatedKeywordRepository relatedKeywordRepository;
    private final RelatedKeywordService relatedKeywordService;

    // 키워드,연관된 키워드 및 가중치 등록
    @PutMapping("/api/keywords/new")
    public ResponseEntity<DefaultResponseDto> putKeyword(@RequestBody RequestKeywordDto requestKeywordDto) {
        return new ResponseEntity<>(relatedKeywordService.setKeyword(requestKeywordDto), HttpStatus.OK);
    }

    // 뉴스 검색 관련어를 반환하는 API입니다.
    // 15개의 언급 관련어를 제공합니다.
    @GetMapping("/api/keyword/search/relastion")
    public ResponseEntity<ResponseKeywordDto> test(@RequestParam String keyword) {
        return relatedKeywordService.getKeyword(keyword);
    }



    @GetMapping( "/api/keyword/search/relastion/news")
    List<Keyword> getPerson() {
        return relatedKeywordRepository.findAll();
    }

    @GetMapping("/keywords/name")
    Keyword getName(@RequestParam String keyword) {
        return relatedKeywordRepository.findByValue(keyword);
    }
}
