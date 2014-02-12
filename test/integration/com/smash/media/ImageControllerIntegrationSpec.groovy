package com.smash.media

import com.smash.user.User
import spock.lang.Specification

class ImageControllerIntegrationSpec extends Specification {

    ImageController controller = new ImageController()

    User user
    Image image

    def setup() {
        user = new User(
                username: "username",
                email: "email@email.com",
                password: "pass"
        ).save(failOnError: true)
        image = new Image(
                title: "title",
                description: "description",
                owner: user,
                dataBlob: [0] * 10 as byte[],
                mimeType: "image/png",
                fileName: "file.png"
        ).save(failOnError: true)
    }

    def "showRaw - Success"() {
        when: "calling action show"
        controller.showRaw(1)

        then: "the response outputstream should contain some data"
        controller.response.outputStream
    }
}
