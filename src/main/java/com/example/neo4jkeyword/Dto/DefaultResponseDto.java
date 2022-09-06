package com.example.neo4jkeyword.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultResponseDto {
    private String msg;
    private int status;
}
