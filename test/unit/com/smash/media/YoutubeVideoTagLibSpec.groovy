package com.smash.media

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(YoutubeVideoTagLib)
class YoutubeVideoTagLibSpec extends Specification {

    def "video - Success"() {
        given: "some attributes"
        String width = "700"
        String height = "410"
        String key = "key"

        and: "a tag"
        String tag = "<iframe width='$width' height='$height' src='//www.youtube-nocookie.com/embed/$key?rel=0' frameborder='0' allowfullscreen=''></iframe>"

        when: "calling tag with default attributes"
        def tag1 = tagLib.video(videoKey : 'key')

        then: "rendered tag should match the given tag"
        tag1.toString() == tag

        when: "calling tag with some attributes"
        def tag2 = tagLib.video(videoKey : 'key', width: width, height: height)

        then: "the rendered tag should match the following"
        tag2.toString() == tag
    }

    def "video - IllegalArgumentException"() {
        when: "calling tag without videoKey attribute"
        tagLib.video(width: '800')

        then: "an IllegalArgumentException should be thrown"
        thrown(IllegalArgumentException)
    }
}
