package com.fc.command.member.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;

public class InmemoryMemberSnapshotRepository implements SnapshotRepository<Member,Email> {
	
	private Map<Email, Snapshot<Member, Email>> repo = new HashMap<>();
	
	@Override
	public Optional<Snapshot<Member, Email>> findLatest(Email id) {
		return Optional.ofNullable(repo.get(id));
	}

	@Override
	public void save(Snapshot<Member, Email> snapshot) {
		repo.put(snapshot.getIdentifier(), snapshot);
	}

}
