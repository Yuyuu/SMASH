package com.smash

import com.smash.media.MediaCut
import com.smash.media.Video
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import com.smash.domain.Comment


class CommentController {

    SpringSecurityService springSecurityService
    CommentService commentService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index(){
        render(view:"index", model: [media:params.media])
    }

   @Secured(['IS_AUTHENTICATED_FULLY'])
    def create(){
       [commentInstance:new Comment(params)]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def save(Comment commentInstance){

        if(!params.containsKey('text')){
            flash.message = message(code: 'comment.text.not.inserted')
            redirect(action:'create')
            return
        }
        if(!params.containsKey('media')){
            flash.message = message(code: 'comment.media.not.found')
            redirect(action:'create')
            return
        }

        commentInstance = commentService.createAndSave(params)
        if (commentInstance.hasErrors()) {
            render(view: "create", model: [commentInstance: commentInstance])
            return
        }

        if(commentInstance.media.hasProperty('videoKey')){
            def mediaCut = MediaCut.findById(commentInstance.media.id)
            redirect(controller:"Video", action: "show", id:mediaCut.id)
        }else
            redirect( controller:'MediaCut', action: "list", userOnly:true)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show(){

        if(!params.containsKey('commentId')){
            redirect( controller:'com.smash.media.MediaCutController', action: "list", userOnly:true)
            return
        }
        def commentInstance = Comment.findById(params.commentId)
        if(!commentInstance){
            redirect( controller:'com.smash.media.MediaCutController', action: "list", userOnly:true)
            return
        }
      //  respond commentInstance
        render(view:"show", model: [commentInstance: commentInstance, currentUser: springSecurityService.currentUser])
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def delete(Comment commentInstance){

        if(!commentInstance){
            redirect( controller:'MediaCut', action: "list", userOnly:true)
            return
        }
        commentInstance = commentService.deleteInstance(commentInstance)
        if(commentInstance.hasErrors()){
            flash.message = message(code: 'comment.not.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.text])
            if(commentInstance.media.hasProperty('videoKey')){
                redirect(controller:"Video", action: "show", id:commentInstance.media.id)
                return
            }else{
                redirect( controller:'MediaCut', action: "list", userOnly:true)
                return
            }

        }
        flash.message = message(code: 'comment.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.text])
        if(commentInstance.media.hasProperty('videoKey')){
            redirect(controller:"Video", action: "show", id:commentInstance.media.id)
        }else{
            redirect( controller:'MediaCut', action: "list", userOnly:true)
        }
    }
}
