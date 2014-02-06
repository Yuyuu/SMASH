package com.smash.domain

import com.smash.media.MediaCut
import com.smash.user.User

class Evaluation {
	boolean type;	//true = positive evaluation, false = negative evaluation
	User author;	
	static belongsTo = [media: MediaCut]

    static constraints = {
		type nullable: false
		author nullable: false
    }
}
