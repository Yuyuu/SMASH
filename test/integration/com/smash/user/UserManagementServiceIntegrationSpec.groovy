package com.smash.user

import grails.plugin.springsecurity.SpringSecurityService
import spock.lang.Specification

class UserManagementServiceIntegrationSpec extends Specification {

    UserManagementService userManagementService
    SpringSecurityService springSecurityService

    def "addUser - OK"() {
        given: "a new user"
        User user = new User(
                username: "Martin",
                email: "martin@mail.com",
                password: "password"
        )

        when: "adding the user"
        userManagementService.addUser(user, RoleEnum.USER_ROLE.role)

        then: "the user is added in the datastore with the proper attributes"
        User u = User.findWhere(username: user.username)
        u != null
        u.enabled
        u.email == user.email
        u.authorities.first().authority == RoleEnum.USER_ROLE.name()
        u.password == springSecurityService.encodePassword("password")
    }

    def "addUser - User has errors"() {
        given: "a user without email"
        User user = new User(
                username: "Martin",
                password: "password"
        )

        when: "adding the user"
        userManagementService.addUser(user, RoleEnum.USER_ROLE.role)

        then: "the user should not be added to the datastore"
        User.findWhere(username: user.username) == null
    }

    def "updateUser"() {
        given: "a user"
        User user = new User(
                username: "username",
                email: "email@email.com",
                password: "pass"
        )

        when: "calling updateUser"
        def updated = userManagementService.updateUser(user)

        then: "the returned user should be in the datastore"
        User.findWhere(username: user.username).id == updated.id
    }
}