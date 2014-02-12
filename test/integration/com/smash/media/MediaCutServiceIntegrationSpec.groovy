package com.smash.media

import com.smash.user.User
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

class MediaCutServiceIntegrationSpec extends Specification {

    MediaCutService mediaCutService

    User user

    def setup() {
        user = new User(
                username: "username",
                email: "email@email.com",
                password: "pass"
        ).save(failOnError: true)
    }

    def retrieveMediaCutById() {
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
        mediaCutService.retrieveMediaCutById(video.id) == video

        and: "null to be returned when the id does not exist"
        mediaCutService.retrieveMediaCutById(video.id + 1) == null
    }

    def "list - List for user"() {
        given: "a new user"
        User userB = new User(
                username: "usernameB",
                email: "emailB@email.com",
                password: "pass"
        ).save(failOnError: true)

        and: "two new videos for two different users"
        new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)
        new Video(
                title: "title",
                description: "description",
                owner: userB,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        when: "calling list method userOnly flag set to true"
        def list = mediaCutService.list(user, true)

        then: "only the videos of the passed user should be returned"
        list.size() == 1
        list.each { assert it.owner == user }
    }

    def "list - List all"() {
        given: "a new user"
        User userB = new User(
                username: "usernameB",
                email: "emailB@email.com",
                password: "pass"
        ).save(failOnError: true)

        and: "two new videos for two different users"
        new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)
        new Video(
                title: "title",
                description: "description",
                owner: userB,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        when: "calling list method with userOnly flag set to false"
        def list = mediaCutService.list(user)

        then: "all the mediacuts should be returned"
        list.size() == 2
    }

    def "updateMediaCut - User is null"() {
        when: "calling updateMediaCut with a null user"
        mediaCutService.updateMediaCut(null, null)

        then: "an IllegalArgumentException should be thrown"
        thrown(IllegalArgumentException)
    }

    def "updateMediaCut - Access denied"() {
        given: "a saved video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        and: "a user"
        User userB = new User(
                username: "usernameB",
                email: "emailB@email.com",
                password: "pass"
        ).save(failOnError: true)

        when: "the user trying to update the video is not the owner"
        mediaCutService.updateMediaCut(userB, video)

        then: "an AccessDeniedException should be thrown"
        thrown(AccessDeniedException)
    }

    def "updateMediaCut - Success"() {
        given: "a saved video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        when: "the owner is trying to delete the video"
        video.title = "updated"
        video.description = "updated"
        def res = mediaCutService.updateMediaCut(user, video)

        then: "the video should not have errors and be updated"
        Video.findWhere(id: video.id) == res
        res.title == "updated"
        res.description == "updated"
    }

    def "deleteMediaCut - User is null"() {
        when: "calling deleteMediaCut with a null user"
        mediaCutService.deleteMediaCut(null, null)

        then: "an IllegalArgumentException should be thrown"
        thrown(IllegalArgumentException)
    }

    def "deleteMediaCut - Access denied"() {
        given: "a saved video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        and: "a user"
        User userB = new User(
                username: "usernameB",
                email: "emailB@email.com",
                password: "pass"
        ).save(failOnError: true)

        when: "the user trying to delete the video is not the owner"
        mediaCutService.deleteMediaCut(userB, video)

        then: "an AccessDeniedException should be thrown"
        thrown(AccessDeniedException)
    }

    def "deleteMediaCut - Success"() {
        given: "a saved video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: user,
                videoKey: "key",
                startTime: 10,
                endTime: 20
        ).save(failOnError: true)

        when: "the owner is trying to delete the video"
        mediaCutService.deleteMediaCut(user, video)

        then: "the video should be removed from the datastore"
        Video.findWhere(id: video.id) == null
    }
}
