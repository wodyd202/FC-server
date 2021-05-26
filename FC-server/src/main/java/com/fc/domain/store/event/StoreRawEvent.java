package com.fc.domain.store.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fc.core.event.RawEvent;
import com.fc.domain.store.Owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STORE_EVENT", indexes = {
	@Index(columnList = "identifiier")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "seq")
public class StoreRawEvent implements RawEvent<Owner>{

	@Id
	@GeneratedValue
	private Long seq;
	
	@Column(nullable = false)
	private Owner identifiier;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private Long version;
	
	@Column(nullable = false)
	private String payload;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createDateTime;
	
	@Override
	public Owner getIdentifier() {
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
	
	public Date getCreateDateTime() {
		return createDateTime;
	}

}
