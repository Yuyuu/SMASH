package com.smash.domain

class MediaCut {
	String name;
	String description;
	MediaType type;
	String fileName;
	static hasMany = [comments: Comment, evaluations: Evaluation, tags: Tag];
	
    static constraints = {
		name blank: false
    }
}

enum MediaType {
	FILM(1),
	MOVIE(2),
	MUSIC(3),
	RADIO(4),
	TV(5)
	
	private final int value;
	
	private MediaType(int val) {
		this.value = val;
	}
	
	int getValue() {
		return this.value;
	}
	
	/**
	 * Indicates if a value is given to a media type
	 * @param val The value to test
	 * @return The media type associated to the value. Or null if there is not.
	 */
	static MediaType containValue(int val) {
		for (value in MediaType.values()) {
			if(value.getValue() == val) {
				return value;
			}
		}
		return null;
	}
}