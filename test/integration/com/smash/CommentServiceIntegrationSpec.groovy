package com.smash

import com.smash.domain.Comment
import com.smash.domain.MediaCut
import com.smash.domain.MediaType
import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import smash.CommentService
import spock.lang.*

class CommentServiceIntegrationSpec extends Specification {

    CommentService commentService
    CommentController commentController
    Comment commentCreated
    User currentUser
    MediaCut mediaCut

    def setup() {
        commentController = new CommentController()
        currentUser = commentService.springSecurityService.currentUser
        mediaCut = new MediaCut(name: "media", type: MediaType.FILM)
        mediaCut.save()
        commentCreated = new Comment(text: "test d'integration CommentService",
                                     media: mediaCut,
                                     author: currentUser)
        commentCreated.save()
    }

    def "create comment test"() {
           given: "params from view"
                Map params = (Map) commentController.create()
                params.text = "new comment"
                params.mediaId = mediaCut.id

            when: "create and save comment"
                Comment newComment = commentService.createAndSave(params)

            then: "comment is added"
                   newComment != null
                   newComment.text != null
                   newComment.text == "new comment"
            /*       Comment commentFound = Comment.findByText("new comment")
                   commentFound != null*/
    }

    def "delete existing comment test"(){
            given:
                Comment commentToDelete = commentCreated
            when:
                commentService.deleteInstance(commentToDelete)
            then:
                Comment.get(commentToDelete.id) == null
    }

}
