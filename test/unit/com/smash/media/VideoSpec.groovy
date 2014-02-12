package com.smash.media

import com.smash.user.User
import spock.lang.Specification
import spock.lang.Unroll

class VideoSpec extends Specification {

    @Unroll
    def "test constraints - duration"(Long startTime, Long endTime, boolean expectedResult) {
        given: "a video"
        Video video = new Video(
                title: "title",
                description: "description",
                owner: Mock(User),
                videoKey: "key",
                startTime: startTime,
                endTime: endTime
        )

        when: "validate method is called"
        def res = video.validate()

        then:
        expectedResult == res
        if (endTime - startTime > Video.MAXIMUM_DURATION) {
            video.errors['endTime'].code == "smash.video.duration.too-long"
        }

        where:
        startTime   | endTime                       | expectedResult
        0           | Video.MAXIMUM_DURATION        | true
        0           | 0                             | false
        0           | Video.MAXIMUM_DURATION + 1    | false
    }
}
