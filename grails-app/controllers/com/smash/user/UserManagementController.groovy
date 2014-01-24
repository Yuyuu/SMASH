package com.smash.user

import grails.plugin.springsecurity.annotation.Secured

class UserManagementController {

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
            redirect(uri: '/login/auth')
        }
    }
}
