package com.fc.core.infra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public interface Validator<T> {
	void validation(T target);
	
	default void assertExistAll(IllegalArgumentException e, Object... objects) {
		for(Object obj : objects) {
			if(obj == null) {
				throw e;
			}
		}
	}
	
	default void assertNotNullObject(Object obj, IllegalArgumentException e) {
		if(obj == null) {
			throw e;
		}
	}
	
	default void assertNotEmptyString(String value, IllegalArgumentException e) {
		if(value == null || value.isEmpty()) {
			throw e;
		}
	}
	
	default void assertRegex(String regex, String value, IllegalArgumentException e) {
		if(!Pattern.matches(regex, value)) {
			throw e;
		}
	}
	
	default void assertNotOverMaxSizeCollectionSize(int max, List<?> list, IllegalArgumentException e) {
		Set<?> set = new HashSet<>(list);
		if(set.size() > max) {
			throw e;
		}
	}
	
	default void assertDuplicateValueInCollection(List<?> list, IllegalArgumentException e){
		Set<?> set = new HashSet<>(list);
		if(set.size() != list.size()) {
			throw e;
		}
	}
	
	default void assertImageFile(MultipartFile file, IllegalArgumentException e) {
		String name = file.getOriginalFilename();
		if(name.isEmpty()) {
			throw e;
		}
		int lastIndexOf = name.lastIndexOf(".");
		String extention = name.substring(lastIndexOf, name.length()).toUpperCase();
		if (!extention.equals(".JPG") && !extention.equals(".JPEG") && !extention.equals(".PNG")) {
			throw e;
		}
	}
}
