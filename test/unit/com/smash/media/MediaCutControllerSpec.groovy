package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import spock.lang.IgnoreRest
import spock.lang.Specification

@TestFor(MediaCutController)
class MediaCutControllerSpec extends Specification {

    MediaCutService mediaCutService
    SpringSecurityService springSecurityService

    def setup() {
        mediaCutService = Mock(MediaCutService)
        springSecurityService = Mock(SpringSecurityService)

        controller.mediaCutService = mediaCutService
        controller.springSecurityService = springSecurityService
    }

    def "list - Default logged in user"() {
        given: "a mocked list"
        List list = Mock(List)

        and: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)
        springSecurityService.isLoggedIn() >> true

        and: "list method of mediacutService returns the mocked list"
        mediaCutService.list((User) _, (Boolean) _) >> list

        when: "calling list action with userOnly = true"
        def res = controller.list(true)

        then: "the following map should be returned"
        res.mediacutRepresentationList == list
        res.userOnly
    }

    def "list - Default anonymous"() {
        given: "a mocked list"
        List list = Mock(List)

        and: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)
        springSecurityService.isLoggedIn() >> false

        and: "list method of mediacutService returns the mocked list"
        mediaCutService.list((User) _, (Boolean) _) >> list

        when: "calling list action with userOnly = true"
        def res = controller.list(true)

        then: "the following map should be returned"
        res.mediacutRepresentationList == list
        !res.userOnly
    }

    def "edit - MediaCut does not exist"() {
        given: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)

        and: "mediaCutService returns null"
        mediaCutService.retrieveMediaCutById((Long) _) >> null

        when: "calling edit action"
        controller.edit(1)

        then: "updateMediaCut method of MediaCutService should not be called"
        0 * mediaCutService.updateMediaCut((User) _, (MediaCut) _)

        then: "the user should be redirected to the following url with a message"
        response.redirectedUrl == '/mediaCut/list?userOnly=true'
        flash.message
    }

    def "edit - Form requested"() {
        given: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)

        and: "mediaCutService returns a mocked video"
        mediaCutService.retrieveMediaCutById((Long) _) >> Mock(Video)

        when: "calling edit action"
        def model = controller.edit(1)

        then: "updateMediaCut method of MediaCutService should not be called"
        0 * mediaCutService.updateMediaCut((User) _, (MediaCut) _)

        then: "the default view should be rendered with the proper model"
        model.mediaCut
        model.user
    }

    def "edit - MediaCut has errors"() {
        given: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)

        and: "request is of type POST"
        request.method = 'POST'

        and: "a mocked video with errors"
        Video video = GroovyMock(Video)
        video.hasErrors() >> true

        and: "mediaCutService returns null"
        mediaCutService.retrieveMediaCutById((Long) _) >> Mock(Video)

        when: "calling edit action"
        def model = controller.edit(1)

        then: "updateMediaCut method of MediaCutService should be called one time and returns the video with errors"
        1 * mediaCutService.updateMediaCut((User) _, (MediaCut) _) >> video

        then: "the default view should be rendered with the proper model"
        model.size() == 2
    }

    def "delete - MediaCut does not exist"() {
        given: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)

        and: "mediaCutService returns null"
        mediaCutService.retrieveMediaCutById((Long) _) >> null

        when: "calling delete action"
        controller.delete(1)

        then: "deleteMediaCut method of MediaCutService should not be called"
        0 * mediaCutService.deleteMediaCut((User) _, (MediaCut) _)

        then: "the user should be redirected to the following url with a message"
        response.redirectedUrl == '/mediaCut/list?userOnly=true'
        flash.message
    }

    def "delete - Success"() {
        given: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)

        and: "mediaCutService returns null"
        mediaCutService.retrieveMediaCutById((Long) _) >> Mock(MediaCut)

        when: "calling delete method of MediaCutService"
        controller.delete(1)

        then: "deleteMediaCut method of MediaCutService should be called one time"
        1 * mediaCutService.deleteMediaCut((User) _, (MediaCut) _)

        then: "the user should be redirected to the following url"
        response.redirectedUrl == '/mediaCut/list?userOnly=true'
        !flash.message
    }
}
