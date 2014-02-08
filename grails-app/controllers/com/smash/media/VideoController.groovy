package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class VideoController {

    SpringSecurityService springSecurityService
    VideoService videoService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show(Long id) {
        if (!id) {
            redirect controller: 'mediaCut', action: 'list'
            return
        }
        Video video = videoService.retrieveVideoById(id)
        if (!video) {
            flash.message = message(code: 'smash.video.not-found', args: [id])
            redirect controller: 'mediaCut', action: 'list'
            return
        }
        [video: video]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create() {
        if (!request.post) {
            def copy = [:] + (flash.chainedParams ?: [:])
            return [video: new Video(copy)]
        }

        Long startTime = (params.start_hr as Long) * 3600 + (params.start_min as Long) * 60 + (params.start_sec as Long)
        Long endTime = (params.end_hr as Long) * 3600 + (params.end_min as Long) * 60 + (params.end_sec as Long)

        if (endTime == 0 || endTime - startTime == 0) {
            flash.message = message(code: 'smash.video.duration.null')
            flash.chainedParams = params
            redirect action: 'create'
            return
        }
        if (endTime - startTime < 0) {
            flash.message = message(code: 'smash.video.duration.negative')
            flash.chainedParams = params
            redirect action: 'create'
            return
        }

        Video video = new Video(
                title: params.title,
                description: params.description,
                videoKey: params.videoKey,
                startTime: startTime,
                endTime: endTime
        )
        video = videoService.addVideo((User) springSecurityService.currentUser, video)
        if (!video || video.hasErrors()) {
            return [video: video]
        }

        redirect(action: 'show', id: video.id)
    }
}
