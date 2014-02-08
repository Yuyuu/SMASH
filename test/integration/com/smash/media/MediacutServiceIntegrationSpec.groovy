package com.smash.media

import com.smash.user.User
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
}
