package com.ohgiraffers.springdatajpa.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Bean 등록 + 내부 메서드 모두 실행
public class BeanConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    /* setter 메소드 미사용 시 ModelMapper가 private 필드에 접근 가능하도록 하는 설정 */
    modelMapper.getConfiguration()
        .setFieldAccessLevel(
            org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        )
        .setFieldMatchingEnabled(true);
    return modelMapper;
  }
}