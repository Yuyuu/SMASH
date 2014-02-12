
package com.smash.media

import com.smash.domain.Tag
import com.smash.tag.TagService;
import com.smash.user.User

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(VideoController)
class VideoControllerSpec extends Specification {

    SpringSecurityService springSecurityService
    VideoService videoService
	TagService tagService;

    def setup() {
        springSecurityService = Mock(SpringSecurityService)
        videoService = Mock(VideoService)
		tagService = Mock(TagService)

        controller.springSecurityService = springSecurityService
        controller.videoService = videoService
		controller.tagService = tagService
    }

    def "show - Id is null"() {
        when: "calling show action with a null id"
        controller.show(null)

        then: "the user should be redirected to the following url"
        response.redirectedUrl == "/mediaCut/list"
    }

    def "show - Video not found"() {
        given: "a stubbed imageService"
        videoService.retrieveVideoById((Long) _) >> null

        when: "calling action show"
        controller.show(1)

        then: "the user should be redirected to the following url with a message"
        response.redirectedUrl == "/mediaCut/list"
        flash.message
    }

    def "show - Success"() {
        given: "a mocked video"
        Video video = Mock(Video)

        and: "a stubbed imageService"
        videoService.retrieveVideoById((Long) _) >> video

        when: "calling action show"
        def model = controller.show(1)

        then: "the following map should be returned"
        model.size() == 2
        model.video == video
    }

    def "create - Form requested"() {
        when: "create action is calling without any method type"
        def model = controller.create()

        then: "the default form should be rendered with the following params"
        model.size() == 1
        model.video
    }

    def "create - Ending time = 0"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "some mocked params"
        params.start_hr = 0
        params.start_min = 0
        params.start_sec = 0
        params.end_hr = 0
        params.end_min = 0
        params.end_sec = 0

        when: "calling create action"
        controller.create()

        then: "the user should be redirected to the following url with a message"
        flash.message
        flash.chainedParams
        response.redirectedUrl == '/video/create'
    }

    def "create - Total time = 0"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "some mocked params"
        params.start_hr = 0
        params.start_min = 1
        params.start_sec = 0
        params.end_hr = 0
        params.end_min = 1
        params.end_sec = 0

        when: "calling create action"
        controller.create()

        then: "the user should be redirected to the following url with a message"
        flash.message
        flash.chainedParams
        response.redirectedUrl == '/video/create'
    }

    def "create - Total time < 0"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "some mocked params"
        params.start_hr = 1
        params.start_min = 0
        params.start_sec = 0
        params.end_hr = 0
        params.end_min = 1
        params.end_sec = 0

        when: "calling create action"
        controller.create()

        then: "the user should be redirected to the following url with a message"
        flash.message
        flash.chainedParams
        response.redirectedUrl == '/video/create'
    }

    def "create - Video could not be saved or has errors"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "some mocked params"
        params.title = "title"
        params.description = "description"
        params.videoKey = "key"
        params.start_hr = 0
        params.start_min = 0
        params.start_sec = 0
        params.end_hr = 0
        params.end_min = 0
        params.end_sec = 25

        and: "a mocked video"
        Video video = GroovyMock(Video)
        video.hasErrors() >> true

        and: "a mocked user"
        User user = Mock(User)
        springSecurityService.currentUser >> user

        and: "a stubbed imageService"
        videoService.addVideo(user, (Video) _) >> video

        when: "calling create action"
        def model = controller.create()

        then: "the following model should be returned"
        model.size() == 1
        model.video == video
    }

    def "create - Success"() {
        given: "request method type is POST"
        request.method = 'POST'

        and: "some mocked params"
        params.title = "title"
        params.description = "description"
        params.videoKey = "key"
        params.start_hr = 0
        params.start_min = 0
        params.start_sec = 0
        params.end_hr = 0
        params.end_min = 0
        params.end_sec = 25

        and: "a mocked video"
        Video video = Mock(Video)
        video.id >> 1

        and: "a mocked user"
        User user = Mock(User)
        springSecurityService.currentUser >> user

        and: "a stubbed imageService"
        videoService.addVideo(user, (Video) _) >> video

        when: "calling create action"
        controller.create()

        then: "the user should be redirected to the following url with the proper model"
        response.redirectedUrl == "/video/show"
    }
	
	def "test create success with tags"() {
		given: "request method type is POST"
        	request.method = 'POST'
		
		and: "some mocked params with tags"
			params.title = "title"
			params.description = "description"
			params.videoKey = "key"
			params.start_hr = 0
			params.start_min = 0
			params.start_sec = 0
			params.end_hr = 0
			params.end_min = 0
			params.end_sec = 25
			params.tags = currentTags

		and: "a mocked video"
			Video video = Mock(Video)
			video.id >> 1

		and: "a mocked user"
			User user = Mock(User)
			springSecurityService.currentUser >> user

		and: "a stubbed videoService"
			videoService.addVideo(user, (Video) _) >> video

		when: "calling create action"
			controller.create()

		then: "the user should be redirected to the following url with the proper model"
			response.redirectedUrl == "/video/show"
		
		and: "TagService shoulb be called"
			expectedTagNumber * tagService.createTag((Tag)_);
			
		where:
			currentTags		| expectedTagNumber
			""				| 0
			"tag1"			| 1
			"tag1 tag2"		| 2
	}
}
