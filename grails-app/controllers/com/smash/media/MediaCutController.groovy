package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class MediaCutController {

    MediacutService mediacutService
    SpringSecurityService springSecurityService

    static defaultAction = "list"

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list(boolean userOnly) {
        User user = (User) springSecurityService.currentUser
        return [
                mediacutRepresentationList: mediacutService.list(user, userOnly),
                userOnly: userOnly
        ]
    }
}
