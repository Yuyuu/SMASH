package com.smash.media

import com.smash.domain.Comment
import com.smash.domain.Evaluation
import com.smash.domain.Tag
import com.smash.user.User

abstract class MediaCut implements IMediaCut {
    String title
    String description
    Date dateCreated
    Date lastUpdated
    int nbPositiveVote = 0;
    int nbNegativeVote = 0;

    static hasMany = [comments: Comment, evaluations: Evaluation, tags: Tag];
    static belongsTo = [owner: User]

    static constraints = {
        title nullable: false, blank: false
        owner nullable: false
        lastUpdated nullable : true
        description nullable: true
        nbPositiveVote nullable: false
        nbNegativeVote nullable: false
    }
}