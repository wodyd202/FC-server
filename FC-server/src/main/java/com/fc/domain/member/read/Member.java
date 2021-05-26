package com.fc.domain.member.read;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member.MemberRule;
import com.fc.domain.member.Member.MemberState;
import com.fc.domain.member.Password;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTable(name = "MEMBER_ADDRESS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "MEMBER_EMAIL"))
@DynamicUpdate
public class Member implements Persistable<Email>{
	
	@EmbeddedId
	@AttributeOverride(column = @Column(name = "email"), name = "value")
	private Email email;

	@Embedded
	@AttributeOverride(column = @Column(name = "password"), name = "value")
	private Password password;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(column = @Column(table = "MEMBER_ADDRESS", nullable = true), name = "longtitude"),
		@AttributeOverride(column = @Column(table = "MEMBER_ADDRESS", nullable = true), name = "letitude"),
		@AttributeOverride(column = @Column(table = "MEMBER_ADDRESS", nullable = true), name = "province"),
		@AttributeOverride(column = @Column(table = "MEMBER_ADDRESS", nullable = true), name = "city"),
		@AttributeOverride(column = @Column(table = "MEMBER_ADDRESS", nullable = true), name = "neighborhood")
	})
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private MemberState state;

	@Enumerated(EnumType.STRING)
	private MemberRule rule;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public Member(Email email) {
		this.email = email;
	}
	
	public void changeAddress(Address address) {
		this.address = address;
	}

	public void changePassword(Password password) {
		this.password = password;
	}

	@Override
	public Email getId() {
		return this.email;
	}

	@Override
	public boolean isNew() {
		return false;
	}
}
