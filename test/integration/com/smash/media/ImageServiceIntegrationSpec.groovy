package com.smash.media

import com.smash.user.User
import spock.lang.Specification

class ImageServiceIntegrationSpec extends Specification {
    
    ImageService imageService

    User user

    def setup() {
        user = new User(
                username: "username",
                email: "email@email.com",
                password: "pass"
        ).save(failOnError: true)
    }

    def retrieveImageById() {
        given: "a saved image"
        Image image = new Image(
                title: "title",
                description: "description",
                owner: user,
                dataBlob: [0, 1, 2, 3, 5] as byte[],
                mimeType: "image/png",
                fileName: "file.png"
        ).save(failOnError: true)

        expect: "the previous image to be returned when passing the right id"
        imageService.retrieveImageById(image.id) == image

        and: "null to be returned when the id does not exist"
        imageService.retrieveImageById(image.id + 1) == null
    }

    def "addImage - User is null"() {
        when: "calling addImage with a null user"
        imageService.addImage(null, new Image())

        then: "an IllegalArgumentException should be thrown"
        def e = thrown(IllegalArgumentException)
        e.message == "User must not be null"
    }

    def "addimage - Success"() {
        given: "an image"
        Image image = new Image(
                title: "title",
                description: "description",
                owner: user,
                dataBlob: [0, 1, 2, 3, 5] as byte[],
                mimeType: "image/png",
                fileName: "file.png"
        )

        when: "calling addImage"
        image = imageService.addImage(user, image)

        then: "the image should be saved and affected to the user"
        !image.hasErrors()
        image.id
        image.owner == user
    }
}
