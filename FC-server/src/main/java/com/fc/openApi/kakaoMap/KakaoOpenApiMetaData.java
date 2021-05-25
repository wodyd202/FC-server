package com.fc.openApi.kakaoMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class KakaoOpenApiMetaData {
	@Value("${kakao-openAPI.serviceKey}")
	private String serviceKey;
	
	@Value("${kakao-openAPI.path}")
	private String path;
	
	@Value("${kakao-openAPI.protocol}")
	private String protocol;
	
	@Value("${kakao-openAPI.host}")
	private String host;
}
