package com.smash

import com.smash.domain.Comment
import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import smash.CommentService
import spock.lang.Specification
import grails.test.mixin.Mock

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock([CommentService,SpringSecurityService,Comment])
@TestFor(CommentController)
class CommentControllerSpec extends Specification {

        CommentService commentService
        SpringSecurityService springSecurityService
        User user
        Comment comment

    def setup() {
        commentService = Mock(CommentService)
        controller.commentService = commentService

        springSecurityService = Mock(SpringSecurityService)
        springSecurityService.currentUser >> user
        controller.springSecurityService = springSecurityService

        comment = Mock(Comment)

    }

    void "test index"(){

        when:" the index function is called"
            controller.index()

        then:"go to the index view"
            view =="/comment/index"
    }

    void "test create"(){
        when:"the create function is called"
            controller.create()

        then:
            view == null
            response.sendRedirect("/comment/create")
    }

    void "test save"(){
        given:
            String viewName
        and:
            commentService.createAndSave(params) >> comment
        when:
            controller.save()

        then:
            response.redirectedUrl == viewName

        where:
            viewName        |   params.text
        "/comment/show"     |   "test"
        "/comment/create"   |   ""

    }

    void "test show"(){
        given:
            Long id
            String viewTemp
        when:
            controller.show(id)
        then:
            view == viewTemp
        where:
            viewTemp            |   id
            "/comment/show"     |    1
            "/comment/index"    |    0

    }
}
