package com.smash

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import smash.CommentService
import com.smash.domain.Comment


class CommentController {

    SpringSecurityService springSecurityService
    CommentService commentService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index(){
        render(view:"index")
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create(){
        // only the current User can add comments to a mediaCut
       [commentInstance: new Comment(params)]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def save(){
        def commentInstance = commentService.createAndSave(params)
        if (commentInstance.hasErrors()) {
            render(view: "create", model: [commentInstance: commentInstance])
            return
        }
        redirect(action: "show", id:commentInstance.id)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show(Long id){

        def commentInstance = Comment.get(id)
        //verify if Comment exists
        if(!commentInstance){
            redirect(action: "index")
            return
        }
        render(view:"show", model: [commentInstance: commentInstance])
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def delete(Long id){

        def commentInstance = Comment.get(id)
        if(!commentInstance){
            redirect(action: "index")
            return
        }
        commentInstance = commentService.deleteInstance(commentInstance)
        if(commentInstance.hasErrors()){
            render(view:"show", model: [commentInstance: commentInstance])
            return
        }
        flash.message = message(code: 'comment.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), id])
        redirect(action: "index")
    }


}
