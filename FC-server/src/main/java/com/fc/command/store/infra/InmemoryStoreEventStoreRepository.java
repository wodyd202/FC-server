package com.fc.command.store.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.domain.store.Owner;
import com.fc.domain.store.event.StoreRawEvent;

public class InmemoryStoreEventStoreRepository implements StoreEventStoreRepository {
	private final Map<Owner, List<StoreRawEvent>> repo = new HashMap<>();
	
	@Override
	public long countByOwner(Owner identifier) {
		List<StoreRawEvent> list = repo.get(identifier);
		return list == null ? 0 : list.size();
	}
	
	@Override
	public List<StoreRawEvent> findByOwner(Owner identifier) {
		List<StoreRawEvent> list = repo.get(identifier);
		return list == null ? new ArrayList<>() : list;
	}

	@Override
	public List<StoreRawEvent> findByOwnerAndVersion(Owner identifier, Long version) {
		List<StoreRawEvent> persistEvents = repo.get(identifier);
		List<StoreRawEvent> events = new ArrayList<>();
		
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
	public void save(StoreRawEvent event) {
		Owner identifier = event.getIdentifier();
		List<StoreRawEvent> list = repo.get(identifier);
		if(list == null) {
			repo.put(identifier, new ArrayList<>());
			list = repo.get(identifier);
		}
		list.add(event);
	}


}
