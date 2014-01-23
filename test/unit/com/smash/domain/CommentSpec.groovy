package com.smash.domain

import com.smash.User
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Comment)
class CommentSpec extends Specification {

    def comment
    def newComment
    def authorComment
    def mediaComment

    @Unroll
    void "test constraints"() {
        setup:
        String longVar = 'a' * 300
        authorComment = new User()
        mediaComment = new MediaCut(type: MediaType.MOVIE)
        comment = new Comment(text: longVar, author: authorComment, media: mediaComment)
        newComment = new Comment(text: testValue, author: authorComment, media: mediaComment)

        expect:
            comment.validate() == false
            newComment.validate() == result

        where:
            testValue   | result
            ''          | false
            'test'      | true


    }
}
