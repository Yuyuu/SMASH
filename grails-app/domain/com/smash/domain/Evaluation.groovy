package com.smash.domain

import com.smash.media.MediaCut
import com.smash.user.User

class Evaluation {
	boolean vote	//true = positive evaluation, false = negative evaluation
	static belongsTo = [media: MediaCut, owner: User]

    static constraints = {
		owner nullable: false
        media nullable : false
        vote nullable: false
    }
}
