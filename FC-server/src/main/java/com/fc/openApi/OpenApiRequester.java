package com.fc.openApi;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public interface OpenApiRequester<R> {
	R request(HttpHeaders header, MultiValueMap<String, String> params);
}
