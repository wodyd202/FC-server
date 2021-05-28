package com.fc.command.member.infra.validator;

import org.springframework.stereotype.Component;

import com.fc.command.common.address.exception.InvalidAddressException;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.core.infra.Validator;

@Component
public class ChangeAddressValidator implements Validator<AddressCommand> {

	@Override
	public void validation(AddressCommand target) {
		double letitude = target.getLetitude();
		double longtitude = target.getLongtitude();
		
		if(letitude <= 0 || longtitude <= 0) {
			throw new InvalidAddressException("좌표값이 잘못되었습니다. 좌표값을 다시 확인해주세요.");
		}
	}

}
