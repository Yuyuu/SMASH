package com.smash

import com.smash.media.MediaCut
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
       flash.clear()
       [commentInstance:new Comment(params)]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def save(Comment commentInstance){

        if(!params.containsKey('text')){
            flash.message = message(code: 'comment.text.not.inserted')
            if(params.media){
                def mediaCut = MediaCut.findById(params.media)
                if(mediaCut){
                    if(commentInstance.media.hasProperty('videoKey'))
                        redirect(controller:"Video", action: "show", id:mediaCut.id)
                    else
                        redirect( controller:'Image', action: "show", id:mediaCut.id)
                }else
                    render(view: "/mediaCut/list", model: [userOnly: true])
            }else
                render(view: "/mediaCut/list", model: [userOnly: true])
        }
        if(!params.containsKey('media')){
            flash.message = message(code: 'comment.media.not.found')
            render(view: "/mediaCut/list", model: [userOnly: true])
            return
        }

        commentInstance = commentService.createAndSave(params)
        if ( !commentInstance  || commentInstance.hasErrors()) {
            redirect(controller: 'mediaCut', action:"list", userOnly:true)
            return
        }

        def mediaCut = MediaCut.findById(commentInstance.media.id)
        if(mediaCut){
            if(commentInstance.media.hasProperty('videoKey'))
                redirect(controller:"Video", action: "show", id:mediaCut.id)
            else
                redirect( controller:'Image', action: "show", id:mediaCut.id)
        }else
            redirect(controller: 'mediaCut', action:"list", userOnly:true)

    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show(){
        if(!params.containsKey('commentId')){
            redirect( controller:'mediaCut', action: "list", userOnly:true)
            return
        }
        def commentInstance = Comment.findById(params.commentId)
        if(!commentInstance){
            redirect( controller:'mediaCut', action: "list", userOnly:true)
            return
        }
        render(view:"show", model: [commentInstance: commentInstance, currentUser: springSecurityService.currentUser])
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def edit(Comment commentInstance){
        if(!commentInstance){
            redirect( controller:'mediaCut', action: "list", userOnly:true)
            return
        }
        String text = commentInstance.text
        if(!params.containsKey('text') || !params.text){
            flash.message = message(code: 'comment.not.edited.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.id])
            redirect( controller:'MediaCut', action: "list", userOnly:true)
            return
        }
        commentInstance = commentService.updateAndSave(commentInstance, params)
        if (commentInstance.hasErrors()) {
            flash.message = message(code: 'comment.not.edited.message', args: [message(code: 'comment.label', default: 'Comment'), text])
            redirect(controller:'MediaCut', action: "list", userOnly:true)
            return
        }

        def mediaCut = MediaCut.findById(commentInstance.media.id)
        if(mediaCut){
            if(commentInstance.media.hasProperty('videoKey'))
                redirect(controller:"Video", action: "show", id:mediaCut.id)
            else
                redirect( controller:'Image', action: "show", id:mediaCut.id)
        }else
            redirect(controller: 'mediaCut', action:"list", userOnly:true)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def delete(Comment commentInstance){

        if(!commentInstance){
            redirect( controller:'mediaCut', action: "list", userOnly:true)
            return
        }
        String textValue = commentInstance.text
        MediaCut mediaCut = commentInstance.media

        commentInstance = commentService.deleteInstance(commentInstance)
        if(commentInstance && commentInstance.hasErrors()){
            flash.message = message(code: 'comment.not.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), textValue])
            if(mediaCut){
                if(commentInstance.media.hasProperty('videoKey'))
                    redirect(controller:"Video", action: "show", id:mediaCut.id)
                else
                    redirect( controller:'Image', action: "show", id:mediaCut.id)
            }else
                redirect(controller: 'mediaCut', action:"list", userOnly:true)
            return
        }

        if(mediaCut){
            if(commentInstance.media.hasProperty('videoKey'))
                redirect(controller:"Video", action: "show", id:mediaCut.id)
            else
                redirect( controller:'Image', action: "show", id:mediaCut.id)
        }else
            redirect(controller: 'mediaCut', action:"list", userOnly:true)
    }
}
