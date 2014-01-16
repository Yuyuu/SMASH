package com.smash.user

import com.smash.user.Role
import com.smash.user.User
import com.smash.user.UserRole
import grails.transaction.Transactional

@Transactional
class UserManagementService {

    /**
     * Adds a new user, the user is enabled by default
     * @param user the user to add
     * @param mainRole the main role of the user
     * @param enabled flag that indicates if the user is enabled
     */
    User addUser(User user, Role mainRole, boolean enabled = true) {
        user.enabled = enabled
        user.save()
        if (!user.hasErrors()) {
            UserRole.create user, mainRole
        }
        user
    }
}
