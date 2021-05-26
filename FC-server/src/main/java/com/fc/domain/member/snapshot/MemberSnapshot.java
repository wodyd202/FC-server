package com.fc.domain.member.snapshot;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fc.core.snapshot.Snapshot;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;

import lombok.Getter;

@Entity
@Getter
@Table(name = "MEMBER_SNAPSHOT")
public class MemberSnapshot extends Snapshot<Member, Email>{
	private static final long serialVersionUID = 1L;

	@Override
	@Convert(converter = MemberAggregateConverter.class)
	public Member getAggregateRoot() {
		return super.getAggregateRoot();
	}
	
	MemberSnapshot() {}
	
	public MemberSnapshot(Email identifier, Long version, Member aggregateRoot) {
		super(identifier, version, aggregateRoot);
	}

	public void change(Snapshot<Member, Email> snapshot) {
		this.aggregateRoot = snapshot.getAggregateRoot();
		this.version = snapshot.getVersion();
	}
}
