package com.smash.media

import com.smash.user.User
import grails.transaction.Transactional

@Transactional
class ImageService {

    /**
     * Retrieves an image by its id
     * @param id
     * @return
     */
    Image retrieveImageById(Long id) {
        Image.findWhere(id: id)
    }

    /**
     * Sets the owner of a created image and saves it in the datastore
     * @param user
     * @param image
     * @return
     */
    Image addImage(User user, Image image) {
        if (!user) {
            throw new IllegalArgumentException("User must not be null")
        }
        image.owner = user
        image.save()
        image
    }
}
