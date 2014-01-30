package com.smash

import com.smash.domain.Comment
import com.smash.domain.MediaCut
import com.smash.user.UserManagementService
import smash.CommentService

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class CommentController {

    SpringSecurityService springSecurityService
    UserManagementService userManagementService

    def commentService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index(){
        render(view:"index")
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create(){

        // only the current User can add comments to a mediaCut
        def commentList = Comment.list()
        [commentInstance: new Comment(params), commentList: commentList]
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
    def show(){

        def commentList = Comment.getAll()
        render(view:"show", [commentInstanceList: commentList])
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def edit(){

        render(view:"edit")
    }
}
