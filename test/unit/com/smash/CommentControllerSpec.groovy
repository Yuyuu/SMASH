package com.smash

import com.smash.domain.Comment
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CommentController)
class CommentControllerSpec extends Specification {

    void "test index"() {
        when:
        controller.index()

        then:
        response.redirectUrl.endsWith("index")

    }
}
