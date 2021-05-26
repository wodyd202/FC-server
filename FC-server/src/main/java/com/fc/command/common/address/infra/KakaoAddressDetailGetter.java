package com.fc.command.common.address.infra;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

import com.fc.command.common.address.exception.InvalidAddressException;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.domain.member.Address;
import com.fc.openApi.OpenApiRequester;
import com.fc.openApi.kakaoMap.KakaoMapOpenApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoAddressDetailGetter implements AddressDetailGetter {
	private final OpenApiRequester<KakaoMapOpenApiResponse> openApiRequester;

	@Override
	public Address getDetail(AddressCommand command) {
		KakaoMapOpenApiResponse response = openApiRequester.request(new HttpHeaders(),
				new LinkedMultiValueMap<String, String>() {
					private static final long serialVersionUID = 1L;
					{
						add("x", Double.toString(command.getLongtitude()));
						add("y", Double.toString(command.getLetitude()));
					}
				});
		if (!response.haveOne()) {
			throw new InvalidAddressException("주소 값이 잘못되었습니다.");
		}
		return new Address(command.getLongtitude(), command.getLetitude(), response.getAddress().getRegion1DepthName(),
				response.getAddress().getRegion2DepthName(), response.getAddress().getRegion3DepthName());
	}

}
