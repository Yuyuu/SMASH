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

        def mediacutRepresentationList = mediaCutService.list(user, userOnly)
        def totalCount = mediacutRepresentationList.size()

        int offset = (params.offset ?: 0) as Integer
        int max = (params.max ?: 4) as Integer

        mediacutRepresentationList = mediacutRepresentationList.subList(
                offset,
                (offset + max < totalCount) ? (offset + max) : totalCount
        )

        return [
                mediacutRepresentationList: mediacutRepresentationList,
                totalCount: totalCount,
                user: user,
                userOnly: userOnly
        ]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def edit(Long id) {
        User user = (User) springSecurityService.currentUser
        MediaCut mediaCut = mediaCutService.retrieveMediaCutById(id)
        if (!mediaCut) {
            flash.message = message(code: 'smash.mediacut.not-found', args: [id])
            redirect(action: 'list', params: [userOnly: true])
            return
        }

        if (!request.post) {
            return [mediaCut: mediaCut, user: user]
        }

        mediaCut.properties = params
        mediaCut = mediaCutService.updateMediaCut(user, mediaCut)
        if (mediaCut.hasErrors()) {
            return [mediaCut: mediaCut, user: user]
        }
        redirect(controller: mediaCut.class.simpleName.toLowerCase(), action: 'show', params: [id: mediaCut.id])
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
