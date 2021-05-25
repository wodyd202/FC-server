package com.fc.command.common.address.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fc.command.common.address.infra.KakaoAddressDetailGetter;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.AddressDetailGetter;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.core.infra.Validator;
import com.fc.openApi.OpenApiRequester;
import com.fc.openApi.kakaoMap.KakaoMapOpenApiResponse;

@Configuration
public class AddressConfig {
	
	@Bean
	AddressDetailGetter addressDetailGetter(OpenApiRequester<KakaoMapOpenApiResponse> openApiRequester) { 
		return new KakaoAddressDetailGetter(openApiRequester);
	}
	
	@Bean
	Validator<AddressCommand> changeAddressValidator(){
		return new ChangeAddressValidator();
	}
}
