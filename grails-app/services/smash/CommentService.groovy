package smash

import com.smash.User
import com.smash.domain.Comment
import com.smash.domain.MediaCut
import grails.transaction.Transactional

@Transactional
class CommentService {

    def springSecurityService

    def createAndSave(){
        User currentUser = springSecurityService.findByUsername(springSecurityService.principal.username)
        MediaCut mediaCut
        mediaCut.name="random mediaCut"
        mediaCut.type='FILM'

        def newComment = new Comment( text: properties.text,
                                      author: currentUser,
                                      mediaCut: mediaCut)

        newComment.save(flush:true)
    }
    def saveComment() {

    }
}
