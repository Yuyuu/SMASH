package com.smash.media

import com.smash.user.User
import grails.transaction.Transactional
import org.springframework.security.access.AccessDeniedException

@Transactional
class MediaCutService {

    /**
     * Retrieves a MediaCut by its id
     * @param id
     * @return
     */
    MediaCut retrieveMediaCutById(Long id) {
        MediaCut.findWhere(id: id)
    }

    /**
     * Retrieves a list of Media Cuts
     * @param user
     * @param userOnly flag that indicates if only the given user's media cuts should be returned
     * @return
     */
    List<MediaCut> list(User user, boolean userOnly = false) {
        List<MediaCut> mediaCutList
        def criteria = MediaCut.createCriteria()

        mediaCutList = criteria {
            if (userOnly) {
                eq("owner.id", user.id)
            }
            order("dateCreated", "desc")
        }

        mediaCutList
    }

    /**
     * Deletes a Media Cut from the datastore
     * @param user
     * @param mediaCut
     * @throws AccessDeniedException if the media cut does not belong to the given user
     */
    def deleteMediaCut(User user, MediaCut mediaCut) {
        if (!user) {
            throw new IllegalArgumentException("User must not be null")
        }
        if (mediaCut.owner != user) {
            throw new AccessDeniedException("Current user is not allowed to access this resource")
        }

        mediaCut.delete()
    }
}
