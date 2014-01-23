package com.smash.domain

import com.smash.user.User

class Comment {
	String text;
	User author;
	static belongsTo = [media: MediaCut];

    static constraints = {
		text blank: false, size: 1..250
		author nullable: false
        media nullable: false
    }

    String getComment(){
        text
    }

}
