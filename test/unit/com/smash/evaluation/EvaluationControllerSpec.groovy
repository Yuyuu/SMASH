package com.smash.evaluation

import com.smash.BootstrapTestService
import com.smash.domain.Evaluation
import com.smash.evaluation.EvaluationController
import com.smash.media.Image
import com.smash.media.MediaCut
import com.smash.media.Video
import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(EvaluationController)
@Mock([Evaluation,MediaCut, Image, User, Video])
class EvaluationControllerSpec extends Specification {

    SpringSecurityService springSecurityService
    BootstrapTestService bootstrapTestService
    EvaluationService evaluationService
    User user

    def setup() {
        springSecurityService = Mock(SpringSecurityService)
        controller.springSecurityService = springSecurityService
        evaluationService = Mock(EvaluationService)
        controller.evaluationService = evaluationService
        user = new User(username: "owner", email: "owner@ups.com", password: "pwd",
                enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save(failOnError: true)
    }

    def cleanup() {
    }

    def "vote - success with an image"() {
        given: "an image mediacut"
            MediaCut media = new Image(title: "Une image", owner: user, blob: new byte[10], mimeType: "image/png",
                    fileName: "my image", dateCreated: new Date()).save(failOnError: true)

        and: "mocked params"
            controller.params.id = "1"
            controller.params.type = "image"


        when: "calling action vote"
            controller.vote()

        then: "the user should be redirected to the following url"
            response.redirectedUrl == "/image/show/1"
    }

    def "vote - success with a video"() {
        given: "a mocked video mediacut"
            MediaCut video = new Video(title: "Une video", owner: user, videoKey: "key",
                    startTime: 0, endTime: 10, dateCreated: new Date()).save(failOnError: true)

        and: "mocked params"
            controller.params.id = "1"
            controller.params.type = "video"

        when: "calling action vote"
            controller.vote()

        then: "the user should be redirected to the following url"
            response.redirectedUrl == "/video/show/1"
    }
}
