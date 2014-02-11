package com.smash.evaluation

import com.smash.domain.Evaluation
import com.smash.media.Image
import com.smash.media.MediaCut
import com.smash.user.User
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(EvaluationTagLib)
@Mock([User, MediaCut, Evaluation, Image])
class EvaluationTagLibSpec extends Specification {

    def evaluations
    User user;

    def setup() {
        user = new User(username: "owner", email: "owner@ups.com", password: "pwd",
                enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save(failOnError: true)
        MediaCut mediacut = new Image(title: "Une image", owner: user, blob: new byte[10],
                mimeType: "image/png", fileName: "my image", dateCreated: new Date()).save(failOnError: true)
        Evaluation eval = new Evaluation(vote: true, owner: user, media: mediacut).save(failOnError: true)
        evaluations = new HashSet<Evaluation>() {{
            add(eval);
        }}
    }

    def cleanup() {
    }

    def "test if user already voted"() {
        given: "some attributes"
            def evaluations = evaluations
            def loggedUserId= user.id

        and: "a html response"
            def html = '<span class=\'hidden\'>OK</span>'

        when: "calling tag"
            def tag = tagLib.ifAlreadyVoted(evaluations: evaluations, loggedUserId: loggedUserId)

        then: "rendered tag should match the given html"
            tag.toString() == html
    }

    def "test if user not already voted"() {
        given: "some attributes"
            def evaluations = evaluations
            def loggedUserId = -1

        and: "a html response"
            def html = '<span class=\'hidden\'>OK</span>'

        when: "calling tag"
            def tag = tagLib.ifNotAlreadyVoted(evaluations: evaluations, loggedUserId: loggedUserId)

        then: "rendered tag should match the given html"
            tag.toString() == html
    }
}
