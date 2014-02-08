package com.smash.media

import com.smash.user.User
import grails.transaction.Transactional

@Transactional
class ImageService {

    Image retrieveImageById(Long id) {
        Image.findWhere(id: id)
    }

    Image addImage(User user, Image image) {
        if (!user) {
            throw new IllegalArgumentException("User must not be null")
        }
        image.owner = user
        image.save()
        image
    }
}
