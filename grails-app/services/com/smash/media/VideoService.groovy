package com.smash.media

import com.smash.user.User
import grails.transaction.Transactional

@Transactional
class VideoService {

    Video retrieveVideoById(Long id) {
        Video.findWhere(id: id)
    }

    Video addVideo(User user, Video video) {
        if (!user) {
            throw new IllegalArgumentException("User must not be null")
        }
        video.owner = user
        video.save()
        video
    }
}
