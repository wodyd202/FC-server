package com.fc.command.common.address.infra;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.domain.member.Address;

public interface AddressDetailGetter {

	Address getDetail(AddressCommand command);
	
}
