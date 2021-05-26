package com.fc.domain.store;

import java.util.Date;

import com.fc.core.domain.AggregateRoot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 aggregate
  */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends AggregateRoot<Owner>{
	private static final long serialVersionUID = 1L;
	public enum StoreState { SELL, NOT_SELL , CLOSED }
	private Owner owner;
	private BusinessDetail detail;
	private StoreTags tags;
	private StoreStyles styles;
	private MainImage image;
	private OpeningHour openingHour;
	private StoreState state;
	private Date createDateTime;
}
