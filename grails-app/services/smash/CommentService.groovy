package smash

import com.smash.user.User
import com.smash.domain.Comment
import com.smash.media.MediaCut
import grails.transaction.Transactional

@Transactional
class CommentService {

    def springSecurityService

    def Comment createAndSave(Map<Object, Object> properties){

        // there is no mediaCut
        def mediaObj
        if(MediaCut.findById(properties.mediaId)){
            mediaObj = MediaCut.findById(properties.mediaId)
        }
        mediaObj.save()

        User user = springSecurityService.currentUser
        def newComment = new Comment( text: properties.text,
                                      author: user,
                                      media: mediaObj)

        newComment.save(flush: true)
        newComment
    }

    def Comment deleteInstance(Comment commentInstance){

        commentInstance.delete()
        commentInstance
    }

}
