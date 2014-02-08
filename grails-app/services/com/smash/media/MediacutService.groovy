package com.smash.media

import com.smash.user.User
import grails.transaction.Transactional

@Transactional
class MediacutService {

    List<MediaCut> list(User user, boolean userOnly = false) {
        if (!user) {
            throw new IllegalArgumentException("User must not be null")
        }

        List<MediaCut> mediaCutList
        def criteria = MediaCut.createCriteria()

        if (userOnly) {
            mediaCutList = criteria {
                eq("owner.id", user.id)
                order("dateCreated", "desc")
            }
        } else {
            mediaCutList = criteria {
                order("dateCreated", "desc")
            }
        }

        mediaCutList
    }
}
