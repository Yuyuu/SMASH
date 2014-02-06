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
@Mock([SpringSecurityService,Comment])
@TestFor(CommentController)
class CommentControllerSpec extends Specification {
        SpringSecurityService springSecurityService
        User user
        Comment comment

    def setup() {
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

    void "test save with errors"(){
        given:
            params.text = null
        when:
            controller.save()
        then:
           response.redirectedUrl == "/comment/create"
    }

    void "test show - id not found"(){
        given:
            Long id
        when:
            controller.show(id)
        then:
            response.redirectedUrl == "/comment/index"
    }
}
