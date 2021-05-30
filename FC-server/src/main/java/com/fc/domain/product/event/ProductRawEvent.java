package com.fc.domain.product.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fc.core.event.RawEvent;
import com.fc.domain.product.ProductId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_EVENT", indexes = {
	@Index(columnList = "identifiier")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "seq")
public class ProductRawEvent implements RawEvent<ProductId>{

	@Id
	@GeneratedValue
	private Long seq;
	
	@Column(nullable = false)
	private ProductId identifiier;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private Long version;
	
	@Lob
	@Column(nullable = false)
	private String payload;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createDateTime;
	
	@Override
	public ProductId getIdentifier() {
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
