package com.fc.service.member.infra;

import java.util.List;

import com.fc.domain.member.Email;
import com.fc.domain.member.event.MemberRawEvent;

public interface MemberEventStoreRepository {
	List<MemberRawEvent> findByEmail(Email email);
	List<MemberRawEvent> findAll();
	List<MemberRawEvent> findByEmailAndVersion(Email identifier, Long version);
	void save(MemberRawEvent event);
}
