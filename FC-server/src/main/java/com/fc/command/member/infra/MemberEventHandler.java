package com.fc.command.member.infra;

import com.fc.core.event.AbstractEventHandler;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;

@SuppressWarnings("rawtypes")
public class MemberEventHandler extends AbstractEventHandler<Member, Email> {

	public MemberEventHandler(EventStore eventStore, SnapshotRepository snapshotRepository) {
		super(eventStore, snapshotRepository);
	}

}
