package com.smash.user

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Role, User])
@TestFor(UserManagementController)
class UserManagementControllerSpec extends Specification {

    UserManagementService userManagementService

    def setup() {
        userManagementService = Mock(UserManagementService)

        controller.userManagementService = userManagementService
    }

    void "signup - Empty params"() {
        given: "a user with errors"
        User user = new User()
        user.errors.rejectValue('username', null)

        and: "addUser method returns the previous user"
        userManagementService.addUser((User) _, null) >> user

        when: "signup action is called"
        controller.signup()

        then: "the following view should be rendered with the proper model"
        view == '/index'
        model.user == user
    }

    void "signup - Passwords are different"() {
        given: "some mocked params with two different passwords"
        params.username = "username"
        params.email = "email@email.com"
        params.password = "password"
        params.password2 = "differentPassword"

        when: "signup action is called"
        controller.signup()

        then: "addUser method of UserManagementService should not be called"
        0 * userManagementService.addUser((User) _, RoleEnum.USER_ROLE.role)

        then: "the following view should be rendered with the proper model"
        view == '/index'
        model.user.username == params.username
        model.user.email == params.email
        model.user.password == params.password
    }

    void "signup - Service returns user with errors"() {
        given: "a user with errors"
        User user = new User(
                username: "username",
                email: "email@email.com",
                password: "password"
        )
        user.errors.rejectValue('email', null)

        and: "some mocked valid params"
        params.username = user.username
        params.email = user.email
        params.password = user.password
        params.password2 = user.password

        when: "signup action is called"
        controller.signup()

        then: "addUser method of UserManagementService should be called one time and returns the previous user"
        1 * userManagementService.addUser((User) _, RoleEnum.USER_ROLE.role) >> user

        then: "the following view should be rendered with the proper model"
        view == '/index'
        model.user.username == params.username
        model.user.email == params.email
        model.user.password == params.password
    }

    void "signup - Sucess"() {
        given: "a valid user"
        User user = new User(
                username: "username",
                email: "email@email.com",
                password: "password"
        )

        and: "some mocked valid params"
        params.username = user.username
        params.email = user.email
        params.password = user.password
        params.password2 = user.password

        when: "signup action is called"
        controller.signup()

        then: "addUser method of UserManagementService should be called one time and returns the previous user"
        1 * userManagementService.addUser((User) _, RoleEnum.USER_ROLE.role) >> user

        then: "a message should be generated and the user should be redirected to the following url"
        flash.message
        response.redirectedUrl == '/login/auth'
    }
}
