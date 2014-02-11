package com.smash

import com.smash.domain.Comment
import com.smash.media.Image
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
            params.media = 1
        when:
            controller.save()
        then:
           response.redirectedUrl == "/mediaCut/list"
           flash.message == "comment.text.not.inserted"
    }

    void "test save - no media added to comment"(){
        given:
            params.text = "test"
        when:
            controller.save()
        then:
            response.redirectedUrl == "/mediaCut/list"
            flash.message == "comment.media.not.found"
    }

    void "test save - comment to video, but params.text = null"(){
        given:
            Video video = Mock(Video)
            params.media = video.id
        and:
            MediaCut.metaClass.static.findById = { Long id -> video }
        when:
            controller.save(new Comment(params))
        then:
            flash.message == "comment.text.not.inserted"
            response.redirectedUrl == "/video/show"
    }

    void "test save - comment to image, but params.text = null"(){
        given:
            Image image = Mock(Image)
            params.media = image.id
        and:
            MediaCut.metaClass.static.findById = { Long id -> image }
        when:
            controller.save(new Comment(params))
        then:
            flash.message == "comment.text.not.inserted"
            response.redirectedUrl == "/image/show"
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
    void "test save - comment to image"(){
        given:
            Image image = Mock(Image)
            params.text = "new comment"
            params.media = image.id
            Comment commentInstance = Mock(Comment)
        and:
            commentInstance.media >> image
        and:
            commentService.createAndSave(params) >> commentInstance
        and:
            MediaCut.metaClass.static.findById = { Long id -> image }
        when:
            controller.save(commentInstance)
        then:
            commentInstance != null
            response.redirectedUrl == "/image/show"
    }

    void "test save - comment to media (not found)"(){
        given:
            params.text = "new comment"
            params.media = media.id
            Comment commentInstance = Mock(Comment)
        and:
            commentInstance.media >> media
        and:
            commentService.createAndSave(params) >> commentInstance
        when:
            controller.save(commentInstance)
        then:
            commentInstance != null
            response.redirectedUrl == "/mediaCut/list"
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
            response.redirectedUrl == "/mediaCut/list"
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
        and:
            MediaCut mediaCut = commentInstance.media
        and:
            commentService.deleteInstance(commentInstance) >> commentInstance
        and:
            MediaCut.metaClass.static.findById = { Long id -> mediaCut }
        when:
            controller.delete(commentInstance)
        then:
            response.redirectedUrl == "/video/show"
    }

    void "test delete - comment to image"(){
        given:
            def commentInstance = Mock(Comment)
            def image = Mock(Image)
            commentInstance.media >> image
        and:
            MediaCut mediaCut = commentInstance.media
        and:
            commentService.deleteInstance(commentInstance) >> commentInstance
        and:
            MediaCut.metaClass.static.findById = { Long id -> mediaCut }
        when:
            controller.delete(commentInstance)
        then:
            response.redirectedUrl == "/image/show"
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

    void "test delete - comment image hasErrors"(){
        given:
            def commentInstance = Mock(Comment)
            Image image = Mock(Image)
            commentInstance.media >> image
        and:
            commentService.deleteInstance(commentInstance) >> commentInstance
        and:
            commentInstance.hasErrors() >> true
        when:
            controller.delete(commentInstance)
        then:
            flash.message == "comment.not.deleted.message"
            response.redirectedUrl == "/image/show"
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
            flash.message == null
            response.redirectedUrl == "/video/show"
    }

    void "test edit - comment to image"(){
        given:
            params.text = "new"
            def commentInstance = Mock(Comment)
            Image image = Mock(Image)
            commentInstance.media >> image
        and:
            commentService.updateAndSave(commentInstance,params) >> commentInstance
        and:
            MediaCut.metaClass.static.findById = { Long id -> image }
        when:
            controller.edit(commentInstance)
        then:
            flash.message == null
            response.redirectedUrl == "/image/show"
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
