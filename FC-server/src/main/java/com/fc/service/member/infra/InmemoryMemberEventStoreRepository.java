package com.fc.service.member.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.domain.member.Email;
import com.fc.domain.member.event.MemberRawEvent;

public class InmemoryMemberEventStoreRepository implements MemberEventStoreRepository {
	private final Map<Email, List<MemberRawEvent>> repo = new HashMap<>();
	
	@Override
	public List<MemberRawEvent> findAll() {
		List<MemberRawEvent> events = new ArrayList<>();
		repo.keySet().forEach(c->{
			events.addAll(repo.get(c));
		});
		return events;
	}
	
	@Override
	public List<MemberRawEvent> findByEmail(Email identifier) {
		List<MemberRawEvent> list = repo.get(identifier);
		return list == null ? new ArrayList<>() : list;
	}

	@Override
	public List<MemberRawEvent> findByEmailAndVersion(Email identifier, Long version) {
		List<MemberRawEvent> persistEvents = repo.get(identifier);
		List<MemberRawEvent> events = new ArrayList<>();
		
		if(persistEvents == null) {
			return events;
		}
		persistEvents.forEach(c->{
			if(c.getVersion() > version) {
				events.add(c);
			}
		});
		
		return events;
	}
	
	@Override
	public void save(MemberRawEvent event) {
		Email identifier = event.getIdentifier();
		List<MemberRawEvent> list = repo.get(identifier);
		if(list == null) {
			repo.put(identifier, new ArrayList<>());
			list = repo.get(identifier);
		}
		list.add(event);
	}

}
