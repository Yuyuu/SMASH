package com.smash.domain

import com.smash.media.MediaCut
import com.smash.user.User
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Comment)
class CommentSpec extends Specification {

    def newComment
    def commentTooLong

    def longVar
    def authorComment
    def mediaComment

    @Unroll
    void "test constraints"() {
        setup:
            longVar = 'a' * 300
            authorComment = Mock(User)
            mediaComment = Mock(MediaCut)
            newComment = new Comment(text: testValue, author: authorComment, media: mediaComment)
            commentTooLong = new Comment(text: longVar, author: authorComment, media: mediaComment)

        expect:
            newComment.validate() == result
            commentTooLong.validate() == false

        where:
            testValue   | result
            'test'      | true
            ''          | false
    }

    void "test methods"(){
        setup:
            Comment commentInstance = new Comment(text: "new comment", author: Mock(User), media: Mock(MediaCut))
            String result = commentInstance.getComment()
            String authorName = commentInstance.getAuthorUsername()

        expect:
            result == commentInstance.text
            authorName == commentInstance.author.username
    }
}
