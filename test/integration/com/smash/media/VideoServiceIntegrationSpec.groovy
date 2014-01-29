package com.smash.media

import com.smash.user.User
import spock.lang.Specification

class VideoServiceIntegrationSpec extends Specification {

    VideoService videoService

    User user

    def setup() {
        user = new User(
                username: "username",
                email: "email@email.com",
                password: "pass"
        ).save(failOnError: true)
    }

    def retrieveVideoById() {
        given: "a saved video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        expect: "the previous video to be returned when passing the right id"
        videoService.retrieveVideoById(video.id) == video

        and: "null to be returned when the id does not exist"
        videoService.retrieveVideoById(video.id + 1) == null
    }

    def "addVideo - User is null"() {
        when: "calling addVideo with a null user"
        videoService.addVideo(null, new Video())

        then: "an IllegalArgumentException should be thrown"
        def e = thrown(IllegalArgumentException)
        e.message == "User must not be null"
    }

    def "addvideo - Success"() {
        given: "a video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        )

        when: "calling addVideo"
        video = videoService.addVideo(user, video)

        then: "the video should be saved and affected to the user"
        !video.hasErrors()
        video.id
        video.owner == user
    }
}
