package com.fc.domain.member.event;

import java.time.LocalDateTime;

import com.fc.core.event.RawEvent;
import com.fc.domain.member.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "seq")
public class MemberRawEvent implements RawEvent<Email>{

	private Long seq;
	private Email identifiier;
	private String type;
	private Long version;
	private String payload;
	private LocalDateTime createDateTime;
	
	@Override
	public Email getIdentifier() {
		return this.identifiier;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public Long getVersion() {
		return this.version;
	}

	@Override
	public String getPayload() {
		return this.payload;
	}
	
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

}
