package com.ohgiraffers.springdatajpa.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Bean 등록 + 내부 메서드 모두 실행
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}