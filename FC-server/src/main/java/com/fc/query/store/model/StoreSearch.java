package com.fc.query.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreSearch {
	private String title;
	private String tag;
	private String style;
	
	private Integer letitude;
	private Integer longtitude;
	private Integer distanceCoordinateDifference;
	
	private String province;
	private String neighborhood;
	private String city;
	
	private int page;
	private int size;
}
