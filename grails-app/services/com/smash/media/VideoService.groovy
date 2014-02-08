package com.smash.media

import com.smash.user.User
import grails.transaction.Transactional

@Transactional
class VideoService {

    /**
     * Retrieves a Video by its id
     * @param id
     * @return
     */
    Video retrieveVideoById(Long id) {
        Video.findWhere(id: id)
    }

    /**
     * Sets the owner of a created video and saves it in the datastore
     * @param user
     * @param video
     * @return
     */
    Video addVideo(User user, Video video) {
        if (!user) {
            throw new IllegalArgumentException("User must not be null")
        }
        video.owner = user
        video.save()
        video
    }
}
