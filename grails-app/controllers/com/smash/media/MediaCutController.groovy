package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class MediaCutController {

    MediaCutService mediaCutService
    SpringSecurityService springSecurityService

    static defaultAction = "list"

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def list(boolean userOnly) {
        User user = (User) springSecurityService.currentUser

        if (!springSecurityService.isLoggedIn()) {
            userOnly = false
        }

        return [
                mediacutRepresentationList: mediaCutService.list(user, userOnly),
                userOnly: userOnly
        ]
    }
}
