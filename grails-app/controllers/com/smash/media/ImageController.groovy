package com.smash.media

import com.smash.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.commons.CommonsMultipartFile

class ImageController {

    SpringSecurityService springSecurityService
    ImageService imageService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def show(Long id) {
        if (!id) {
            redirect controller: 'mediaCut', action: 'list'
            return
        }
        Image image = imageService.retrieveImageById(id)
        if (!image) {
            flash.message = message(code: 'smash.image.not-found', args: [id])
            redirect controller: 'mediaCut', action: 'list'
            return
        }
        [image: image]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def create() {
        if (!request.post) {
            def copy = [:] + (flash.chainedParams ?: [:])
            return [image: new Image(copy)]
        }

        params.mimeType = ((CommonsMultipartFile) params.blob).contentType
        params.fileName = ((CommonsMultipartFile) params.blob).originalFilename

        Image image = new Image(params)
        image = imageService.addImage((User) springSecurityService.currentUser, image)
        if (!image || image.hasErrors()) {
            return [image: image]
        }

        redirect(action: 'show', id: image.id)
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def showRaw(Long id) {
        if (!id) {
            redirect controller: 'mediaCut', action: 'list'
            return
        }
        Image image = imageService.retrieveImageById(id)
        if (!image) {
            flash.message = message(code: 'smash.image.not-found', args: [id])
            redirect controller: 'mediaCut', action: 'list'
            return
        }

        response.contentType = image.mimeType
        response.setContentLength(image.blob.size())
        response.outputStream << image.blob
        response.outputStream.flush()
    }
}
