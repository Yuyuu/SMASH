package smash

import com.smash.user.User
import com.smash.domain.Comment
import com.smash.domain.MediaCut
import grails.transaction.Transactional

@Transactional
class CommentService {

    def springSecurityService
   // def mediaObj = MediaCut.findById(properties.mediaId)

    def createAndSave(){
        User currentUser = springSecurityService.findByUsername(springSecurityService.principal.username)
        def newComment = new Comment( text: properties.text,
                                      author: currentUser,
                                      mediaCut: mediaObj)

        newComment.save(flush:true)
    }


}
