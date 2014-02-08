package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
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
}
