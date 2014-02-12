package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import org.springframework.web.multipart.commons.CommonsMultipartFile
import spock.lang.Specification

@TestFor(ImageController)
class ImageControllerSpec extends Specification {

    SpringSecurityService springSecurityService
    ImageService imageService

    def setup() {
        springSecurityService = Mock(SpringSecurityService)
        imageService = Mock(ImageService)

        controller.springSecurityService = springSecurityService
        controller.imageService = imageService
    }

    def "show - Id is null"() {
        when: "calling show action with a null id"
        controller.show(null)

        then: "the user should be redirected to the following url"
        response.redirectedUrl == "/mediaCut/list"
    }

    def "show - Image not found"() {
        given: "a stubbed imageService"
        imageService.retrieveImageById((Long) _) >> null

        when: "calling action show"
        controller.show(1)

        then: "the user should be redirected to the following url with a message"
        response.redirectedUrl == "/mediaCut/list"
        flash.message
    }

    def "show - Success"() {
        given: "a mocked image"
        Image image = Mock(Image)

        and: "a stubbed imageService"
        imageService.retrieveImageById((Long) _) >> image

        when: "calling action show"
        def model = controller.show(1)

        then: "the following map should be returned"
        model.size() == 2
        model.image == image
    }

    def "create - Form requested"() {
        when: "create action is calling without any method type"
        def model = controller.create()

        then: "the default form should be rendered with the following params"
        model.size() == 1
        model.image
    }

    def "create - Image could not be saved or has errors"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "a mocked CommonsMultipartFile"
        CommonsMultipartFile cmf = Mock(CommonsMultipartFile)
        cmf.contentType >> "image/png"
        cmf.originalFilename >> "file.png"

        and: "some mocked params"
        params.title = "title"
        params.description = "description"
        params.blob = cmf

        and: "a mocked image"
        Image image = GroovyMock(Image)
        image.hasErrors() >> true

        and: "a mocked user"
        User user = Mock(User)
        springSecurityService.currentUser >> user

        and: "a stubbed imageService"
        imageService.addImage(user, (Image) _) >> image

        when: "calling create action"
        def model = controller.create()

        then: "the following model should be returned"
        model.size() == 1
        model.image == image
    }

    def "create - Success"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "a mocked CommonsMultipartFile"
        CommonsMultipartFile cmf = Mock(CommonsMultipartFile)
        cmf.contentType >> "image/png"
        cmf.originalFilename >> "file.png"

        and: "some mocked params"
        params.title = "title"
        params.description = "description"
        params.blob = cmf

        and: "a mocked image"
        Image image = Mock(Image)
        image.id >> 1

        and: "a mocked user"
        User user = Mock(User)
        springSecurityService.currentUser >> user

        and: "a stubbed imageService"
        imageService.addImage(user, (Image) _) >> image

        when: "calling create action"
        controller.create()

        then: "the user should be redirected to the following url with the proper model"
        response.redirectedUrl == "/image/show"
    }

    def "showRaw - Id is null"() {
        when: "calling showRaw action with a null id"
        controller.showRaw(null)

        then: "the user should be redirected to the following url"
        response.redirectedUrl == "/mediaCut/list"
    }

    def "showRaw - Image not found"() {
        given: "a stubbed imageService"
        imageService.retrieveImageById((Long) _) >> null

        when: "calling action show"
        controller.showRaw(1)

        then: "the user should be redirected to the following url with a message"
        response.redirectedUrl == "/mediaCut/list"
        flash.message
    }
}
