package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(MediaCutController)
class MediaCutControllerSpec extends Specification {

    MediacutService mediacutService
    SpringSecurityService springSecurityService

    def setup() {
        mediacutService = Mock(MediacutService)
        springSecurityService = Mock(SpringSecurityService)

        controller.mediacutService = mediacutService
        controller.springSecurityService = springSecurityService
    }

    def "list"() {
        given: "a mocked list"
        List list = Mock(List)

        and: "a stubbed current user"
        springSecurityService.currentUser >> Mock(User)

        and: "list method of mediacutService returns the mocked list"
        mediacutService.list((User) _, (Boolean) _) >> list

        when: "calling list action with userOnly = true"
        def res = controller.list(true)

        then: "the following map should be returned"
        res.mediacutRepresentationList == list
        res.userOnly
    }
}