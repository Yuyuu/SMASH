package com.smash.media

import com.smash.domain.Tag
import com.smash.tag.TagService;
import com.smash.user.User

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class VideoController {

    SpringSecurityService springSecurityService
    VideoService videoService
	TagService tagService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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
        [video: video, user: (User) springSecurityService.currentUser]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
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
		
		String tags = params.tags;
		List<Tag> tagList = new ArrayList<Tag>();
		if(tags != null && !tags.empty) {
			for (tag in tags.split(" ")) {
				tagList.add(tagService.createTag(new Tag(name: tag)));
			}
		}

        Video video = new Video(
                title: params.title,
                description: params.description,
                videoKey: params.videoKey,
                startTime: startTime,
                endTime: endTime,
				tags: tagList
        )
        video = videoService.addVideo((User) springSecurityService.currentUser, video)
        if (!video || video.hasErrors()) {
            return [video: video]
        }

        redirect(action: 'show', id: video.id)
    }
}
