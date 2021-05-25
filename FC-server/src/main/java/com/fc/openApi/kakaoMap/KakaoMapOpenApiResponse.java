package com.fc.openApi.kakaoMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoMapOpenApiResponse {
	private final KakaoMapOpenApiResponseAddress address;
	private final KakaoMapOpenApiResponseMeta meta;
	
	public boolean haveOne() {
		return meta.getCount() == 1;
	}
}
