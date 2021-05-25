package com.fc.openApi.kakaoMap;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fc.openApi.OpenApiRequester;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoMapOpenApiRequester implements OpenApiRequester<KakaoMapOpenApiResponse> {
	private final KakaoOpenApiMetaData metaData;
	
	@Override
	public KakaoMapOpenApiResponse request(HttpHeaders header, MultiValueMap<String, String> params) {
		RestTemplate restTpl = new RestTemplate();
		header.add(HttpHeaders.AUTHORIZATION, metaData.getServiceKey());
		URI uri = UriComponentsBuilder.newInstance()
				.scheme(metaData.getProtocol())
				.host(metaData.getHost())
				.path(metaData.getPath())
				.queryParams(params)
				.build()
				.encode().toUri();
		HashMap<?,?> response = restTpl.exchange(uri, HttpMethod.GET, new HttpEntity<Object>(header), HashMap.class)
				.getBody();
		return responseToKakaoOpenApiResponse(response);
	}
	
	@SuppressWarnings("unchecked")
	private KakaoMapOpenApiResponse responseToKakaoOpenApiResponse(HashMap<?, ?> response) {
		HashMap<?, ?> meta = (HashMap<?, ?>) response.get("meta");
		List<HashMap<?, ?>> documents = (List<HashMap<?, ?>>) response.get("documents");

		int totalCount = Integer.parseInt(meta.get("total_count").toString());
		KakaoMapOpenApiResponseMeta kakaoMapOpenApiResponseMeta = new KakaoMapOpenApiResponseMeta(totalCount);
		KakaoMapOpenApiResponseAddress kakaoMapOpenApiResponseAddress = null;
		if(totalCount == 1) {
			HashMap<?, ?> address = (HashMap<?, ?>) documents.get(0).get("address");
			String addressName = address.get("address_name").toString();
			String region1DepthName = address.get("region_1depth_name").toString();
			String region2DepthName = address.get("region_2depth_name").toString();
			String region3DepthName = address.get("region_3depth_name").toString();
			kakaoMapOpenApiResponseAddress = new KakaoMapOpenApiResponseAddress(addressName,region1DepthName,region2DepthName,region3DepthName);
		}
		return new KakaoMapOpenApiResponse(kakaoMapOpenApiResponseAddress, kakaoMapOpenApiResponseMeta);
	}

}
