package com.smash

import com.smash.domain.Comment
import com.smash.media.MediaCut
import com.smash.media.Video
import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.Mock

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock([SpringSecurityService,Comment,MediaCut,Video])
@TestFor(CommentController)
class CommentControllerSpec extends Specification {
        CommentService commentService
        SpringSecurityService springSecurityService
        User user
        Comment comment
        MediaCut media

    def setup() {
        springSecurityService = Mock(SpringSecurityService)
        springSecurityService.currentUser >> user
        controller.springSecurityService = springSecurityService
        commentService = Mock(CommentService)
        controller.commentService = commentService
        comment = Mock(Comment)
        media = Mock(MediaCut)
        comment.media = media
    }

    void "test index"(){
        when:" the index function is called"
            controller.index()

        then:"go to the index view"
            view =="/comment/index"
    }

    void "test create"(){
        given:
            Comment commentInstance
        when:"the create function is called"
            controller.create()
        then:
            view == null
            response.sendRedirect("/comment/create")
            model.commentInstance == commentInstance
    }

    void "test save - no text added to comment"(){
        given:
            params.text = null
        when:
            controller.save()
        then:
           response.redirectedUrl == "/comment/create"
           flash.message != null
    }

    void "test save - no media added to comment"(){
        given:
            params.media = null
        when:
            controller.save()
        then:
            response.redirectedUrl == "/comment/create"
            flash.message != null
    }

    void "test save - succes"(){
        given:
            params.text = "new comment"
            params.media = media.id
            Comment commentInstance = new Comment(params)
        and:
            commentService.createAndSave(params) >> commentInstance
        when:
            controller.save(commentInstance)
        then:
            commentInstance != null
            commentInstance.text == params.text
            response.redirectedUrl == "/mediaCut/list"
    }

    void "test save - comment to video"(){
        given:
            Video video = Mock(Video)
            params.text = "new comment"
            params.media = video.id
            Comment commentInstance = Mock(Comment)
        and:
            commentInstance.media >> video
        and:
            commentService.createAndSave(params) >> commentInstance
        and:
            MediaCut.metaClass.static.findById = { Long id -> video }
        when:
            controller.save(commentInstance)
        then:
            commentInstance != null
            response.redirectedUrl == "/video/show"
    }

    void "test save - comment hasErrors"(){
        given:
            params.media = 1
        and:
            params.text = "test"
            Comment commentInstance = new Comment(params)
        when:
            controller.save(commentInstance)
        and:
            commentInstance = commentService.createAndSave(params)
        then:
            view == "/comment/create"
            model.commentInstance == commentInstance
    }

    void "test show - id not found"(){
        when:
            controller.show()
        then:
            response.redirectedUrl == "/mediaCut/list"
    }

    void "test show - comment not found"(){
        given:
            params.commentId = 2
        when:
            controller.show()
        and:
            def commentInstance = Comment.findById(params.commentId)
        then:
            commentInstance == null
            response.redirectedUrl == "/mediaCut/list"
    }

    void "test show"(){
        given:
            def commentInstance = new Comment(text: "new test", author: user, media: media)
            params.commentId = commentInstance.id
        and:
            Comment.metaClass.static.findById = { Long id -> commentInstance }
        when:
            controller.show()
        then:
            commentInstance != null
            view == "/comment/show"
            model.commentInstance == commentInstance
            model.currentUser == user
    }

    void "test delete"(){
        given:
            def commentInstance = Mock(Comment)
        when:
            controller.delete(commentInstance)
        then:
            flash.message == "comment.deleted.message"
    }

    void "test delete - comment does not exist"(){
        given:
            def commentInstance = null
        when:
            controller.delete(commentInstance)
        then:
            response.redirectedUrl == "/mediaCut/list"
            flash.message == null
    }

    void "test delete - comment to video"(){
        given:
            def commentInstance = Mock(Comment)
            def video = Mock(Video)
            commentInstance.media >> video
        when:
            controller.delete(commentInstance)
        then:
            response.redirectedUrl == "/video/show"
    }

    void "test delete - comment hasErrors"(){
        given:
            def commentInstance = Mock(Comment)
        and:
            commentService.deleteInstance(commentInstance) >> commentInstance
        and:
            commentInstance.hasErrors() >> true
        when:
            controller.delete(commentInstance)
        then:
            flash.message == "comment.not.deleted.message"
            response.redirectedUrl == "/mediaCut/list"
    }
    void "test delete - comment video hasErrors"(){
        given:
            def commentInstance = Mock(Comment)
            Video video = Mock(Video)
            commentInstance.media >> video
        and:
            commentService.deleteInstance(commentInstance) >> commentInstance
        and:
            commentInstance.hasErrors() >> true
        when:
            controller.delete(commentInstance)
        then:
            flash.message == "comment.not.deleted.message"
            response.redirectedUrl == "/video/show"
    }

    void "test edit- comment does not exist"(){
        given:
            def commentInstance = null
        when:
            controller.edit(commentInstance)
        then:
            response.redirectedUrl == "/mediaCut/list"
            flash.message == null
    }

    void "test edit - params.text null"(){
        given:
            Comment commentInstance = Mock(Comment)
            params.text = null
        when:
            controller.edit(commentInstance)
        then:
            flash.message == "comment.not.edited.message"
            response.redirectedUrl == "/mediaCut/list"
    }
    void "test edit -comment"(){
        given:
            params.text = "new"
            def commentInstance = Mock(Comment)
        and:
            commentService.updateAndSave(commentInstance,params) >> commentInstance
        when:
            controller.edit(commentInstance)
        then:
            flash.message == "comment.edited.message"
            response.redirectedUrl == "/mediaCut/list"
    }

    void "test edit - comment to video"(){
        given:
            params.text = "new"
            def commentInstance = Mock(Comment)
            Video video = Mock(Video)
            commentInstance.media >> video
        and:
            commentService.updateAndSave(commentInstance,params) >> commentInstance
        and:
            MediaCut.metaClass.static.findById = { Long id -> video }
        when:
            controller.edit(commentInstance)
        then:
            flash.message == "comment.edited.message"
            response.redirectedUrl == "/video/show"
    }

    void "test edit - comment hasErrors"(){
        given:
            params.text = "new text"
            def commentInstance = Mock(Comment)
        and:
            commentService.updateAndSave(commentInstance,params) >> commentInstance
        and:
            commentInstance.hasErrors() >> true
        when:
            controller.edit(commentInstance)
        then:
            flash.message == "comment.not.edited.message"
            response.redirectedUrl == "/mediaCut/list"
    }

}
