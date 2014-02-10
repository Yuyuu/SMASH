package com.smash

import com.smash.domain.Comment
import com.smash.media.MediaCut
import com.smash.media.Video
import com.smash.user.User
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
        mediaCut = new Video()
        mediaCut.save()
        commentCreated = new Comment(text: "test d'integration CommentService",
                                     media: mediaCut,
                                     author: currentUser)
        commentCreated.save()
    }

    def "test - create comment test"() {
       given: "params from view"
            Map params = (Map) commentController.create()
            params.text = "new comment"
            params.mediaId = mediaCut.id

       when: "create and save comment"
            Comment newComment = commentService.createAndSave(params)
       then: "comment is added"
            newComment
            newComment != null
            newComment.text == "new comment"
    }
    def "test - create and save comment"(){
        given: "params from create view"
            Map params = (Map) commentController.create()
        and:
            params.text = "new comment"
            params.mediaId = mediaCut.id
        and:"create and save comment"
            Comment newComment = commentService.createAndSave(params)
        when:
            commentController.save(newComment)
        then: "comment is added"
           commentController.params.id == mediaCut.id

    }

    def "test - delete existing comment "(){
        given:
            Comment commentToDelete = commentCreated
        when:
            commentService.deleteInstance(commentToDelete)
        then:
            Comment.get(commentToDelete.id) == null
    }

    def "test - edit comment"(){
        given:
            Map params = (Map) commentController.create()
        and:
            params.text = "comment edited"
        and:
            Comment commentInstance = commentCreated
        when:
            commentService.updateAndSave(commentInstance,params)
        then:
            commentInstance.text == params.text
    }

}
