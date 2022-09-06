package com.example.neo4jkeyword.Service;

import com.example.neo4jkeyword.Dto.DefaultResponseDto;
import com.example.neo4jkeyword.Dto.RequestKeywordDto;
import com.example.neo4jkeyword.Dto.ResponseKeywordDto;
import com.example.neo4jkeyword.Node.Keyword;
import com.example.neo4jkeyword.Repository.RelatedKeywordRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatedKeywordService {
    private final RelatedKeywordRepository relatedKeywordRepository;
    // 키워드 등록 및 연관 키워드, 가중치 등록
    // 키워드 노드를 다 생성하고 난 다음, 해당 키워드와 연관 키워드의 관꼐 매핑이 필요
    public DefaultResponseDto setKeyword(RequestKeywordDto requestKeywordDto) {
        String keyword = requestKeywordDto.getKeyword();
        searchValue(keyword);

        Iterator<String> RelatedKeywords = requestKeywordDto.getRelatedKeyword();
        while (RelatedKeywords.hasNext()) {
            String relatedKeyword = RelatedKeywords.next();
            searchValue(relatedKeyword);
            Integer weight = requestKeywordDto.getWeight(relatedKeyword);
            searchRelatedValue(keyword,relatedKeyword);
        }

        return DefaultResponseDto.builder()
                .msg("키워드 저장에 성공했습니다.")
                .status(200)
                .build();
    }

    // 키워드 조회
    public ResponseEntity<ResponseKeywordDto> getKeyword(String keyword) {
        List<Keyword> relatedKeyword = relatedKeywordRepository.findByAllRelatedKeyword(keyword);
        return new ResponseEntity<>(new ResponseKeywordDto(keyword, relatedKeyword), HttpStatus.OK);
    }

    // -----------------------------------------------------------------------------------------------
    // 서브 메소드
    // 해당 키워드가 DB에 없을경우, 키워드를 등록
    public void searchValue(String keyword) {
        if (relatedKeywordRepository.findByValue(keyword) == null) {
            relatedKeywordRepository.queryByInsertValue(keyword);
        }
    }

    // 관계 여부 확인 - 없으면 관계 생성, 있으면 가중치 업데이트
    public void searchRelatedValue(String keyword, String relatedKeyword) {
        Integer setWeight = relatedKeywordRepository.findByRelatedValue(keyword, relatedKeyword);
        if(setWeight == null) {
            relatedKeywordRepository.queryByRelated(keyword,relatedKeyword,1);
//            relatedKeywordRepository.queryByRelated2(keyword,relatedKeyword,1);
        } else {
            // 등록되어있는 관계의 weight의 값을 업데이트하도록 -> 불러와서 사용
            setWeight += 1;
            Integer weight = relatedKeywordRepository.findByRelatedValue(keyword, relatedKeyword);
            System.out.println("keyword = " + keyword + " relatedKeyword = " + relatedKeyword);
            try{
                relatedKeywordRepository.queryBySetRelatedValue(keyword,weight,relatedKeyword,setWeight);
            } catch (Exception e) {
                System.out.println("NoSuchRecordException");
            }
        }
    }
}
