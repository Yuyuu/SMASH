package com.smash

import com.smash.user.User
import com.smash.domain.Comment
import com.smash.media.MediaCut
import grails.transaction.Transactional

@Transactional
class CommentService {

    def springSecurityService

    def Comment createAndSave(Map<Object, Object> properties){

        User user = springSecurityService.currentUser
        def newComment = new Comment( text: properties.text,
                author: user,
                media: MediaCut.findById(properties.media))

        newComment.save(flush:false)
        newComment
    }

    def Comment deleteInstance(Comment commentInstance){

        commentInstance.delete()
        commentInstance
    }

}
