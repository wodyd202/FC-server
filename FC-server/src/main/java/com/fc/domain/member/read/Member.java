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

import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.Password;
import com.fc.domain.member.Member.MemberRule;
import com.fc.domain.member.Member.MemberState;

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
public class Member {
	
	@EmbeddedId
	@AttributeOverride(column = @Column(name = "email"), name = "value")
	private Email email;

	@Embedded
	@AttributeOverride(column = @Column(name = "password", nullable = false), name = "value")
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
	@Column(nullable = false)
	private MemberState state;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRule rule;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createDateTime;

	public void changeAddress(Address address) {
		this.address = address;
	}

	public void changePassword(Password password) {
		this.password = password;
	}
}