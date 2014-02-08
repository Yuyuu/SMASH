package com.smash.media

import com.smash.user.User
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Image)
class ImageSpec extends Specification {

    void "test constraints - Image size too big"() {
        given: "an image exceeding the allowed maximim size"
        Image image = new Image(
                title: "title",
                description: "description",
                owner: Mock(User),
                blob: [0] * (Image.MAXIMUM_SIZE + 1) as byte[],
                mimeType: "image/png",
                fileName: "file.png"
        )

        expect: "the image not to be validated"
        !image.validate()
    }

    void "test constraints - Image size ok"() {
        given: "an image with the maximum size"
        Image image = new Image(
                title: "title",
                description: "description",
                owner: Mock(User),
                blob: [0] * (Image.MAXIMUM_SIZE) as byte[],
                mimeType: "image/png",
                fileName: "file.png"
        )

        expect: "the image to be validated"
        image.validate()
    }
}
