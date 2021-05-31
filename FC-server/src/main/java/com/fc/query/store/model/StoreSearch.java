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
	
	private Integer letitude;
	private Integer longtitude;
	private Integer distanceCoordinateDifference;
	
	private String province;
	private String neighborhood;
	private String city;
	
	private int page;
	private int size;
	
	public boolean emptyAll() {
		if(title == null || title.isEmpty()) {} else { return false; }
		if(letitude == null) {} else { return false; }
		if(longtitude == null) {} else { return false; }
		if(distanceCoordinateDifference == null) {} else { return false; }
		if(province == null || province.isEmpty()) {} else { return false; }
		if(neighborhood == null || neighborhood.isEmpty()) {} else { return false; }
		if(city == null || city.isEmpty()) {} else { return false; }
		return true;
	}
}
