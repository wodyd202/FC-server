package com.fc.openApi.kakaoMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoMapOpenApiResponseAddress {
	private String addressName;
	private String region1DepthName;
	private String region2DepthName;
	private String region3DepthName;
}
