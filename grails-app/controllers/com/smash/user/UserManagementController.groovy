package com.smash.user

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class UserManagementController {

    SpringSecurityService springSecurityService
    UserManagementService userManagementService

    @Secured(['ROLE_ANONYMOUS'])
    def signup() {
        Role mainRole = RoleEnum.USER_ROLE.role
        User user = new User(params)
        if (params.password == params.password2) {
            user = userManagementService.addUser(user, mainRole)
        } else {
            user.errors.rejectValue(
                    'password',
                    'smash.user.password.different',
                    'The specified passwords are different.'
            )
        }

        if (user.hasErrors()) {
            render(view: '/index', model: [user: user])
        } else {
            flash.message = message(code: 'smash.user.sign-up.success')
            redirect uri: '/login/auth'
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def update() {
        User user = (User) springSecurityService.currentUser

        if (!request.post) {
            // display default view
            return [user: user]
        }

        if (params.password != params.password2) {
            user.errors.rejectValue(
                    'password',
                    'smash.user.password.different',
                    'The specified passwords are different.')
        } else {
            if (!params.password) {
                params.remove('password')
            }
            user.properties = params
            user = userManagementService.updateUser(user)
        }

        if (user.hasErrors()) {
            render(view: 'update', model: [user: user])
        } else {
            springSecurityService.reauthenticate user.username
            flash.message = "Your account has been updated successfully"
            redirect action: 'update'
        }
    }
}
