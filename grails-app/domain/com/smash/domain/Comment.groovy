package com.smash.domain

import com.smash.User

class Comment {
	String text;
	User author;
	static belongsTo = [media: MediaCut];

    static constraints = {
		text blank: false 
		author nullable: false
    }
}
