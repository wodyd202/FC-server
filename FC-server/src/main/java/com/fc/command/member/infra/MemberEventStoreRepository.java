package com.fc.command.member.infra;

import java.util.List;

import com.fc.domain.member.Email;
import com.fc.domain.member.event.MemberRawEvent;

public interface MemberEventStoreRepository {
	long countByEmail(Email identifier);
	List<MemberRawEvent> findByEmail(Email identifier);
	List<MemberRawEvent> findByEmailAndVersion(Email identifier, Long version);
	void save(MemberRawEvent event);
}
