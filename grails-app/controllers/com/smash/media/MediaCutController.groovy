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
                user: user,
                userOnly: userOnly
        ]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def delete(Long id) {
        User user = (User) springSecurityService.currentUser
        MediaCut mediaCut = mediaCutService.retrieveMediaCutById(id)
        if (!mediaCut) {
            flash.message = message(code: 'smash.mediacut.not-found', args: [id])
            redirect(action: 'list', params: [userOnly: true])
            return
        }

        mediaCutService.deleteMediaCut(user, mediaCut)
        redirect(action: 'list', params: [userOnly: true])
    }
}
