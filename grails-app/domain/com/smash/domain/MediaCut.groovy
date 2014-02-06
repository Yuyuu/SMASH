package com.smash.domain

class MediaCut {
	String name;
	String description;
	Long duration;
	MediaType type;
	static hasMany = [comments: Comment, evaluations: Evaluation, tags: Tag];
	
    static constraints = {
		name blank: false
    }
}

enum MediaType {
	FILM, MOVIE, MUSIC, RADIO, TV 
}