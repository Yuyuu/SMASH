package com.smash.user

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Role, User])
@TestFor(UserManagementController)
class UserManagementControllerSpec extends Specification {

    UserManagementService userManagementService
    SpringSecurityService springSecurityService

    User user

    def setup() {
        user = new User(
                username: "username",
                email: "email@email.com",
                password: "password"
        )

        userManagementService = Mock(UserManagementService)
        controller.userManagementService = userManagementService
        springSecurityService = Mock(SpringSecurityService)
        springSecurityService.currentUser >> user
        controller.springSecurityService = springSecurityService
    }

    void "signup - Empty params"() {
        given: "an empty user"
        User u = new User()
        u.validate()

        and: "addUser method returns the previous user"
        userManagementService.addUser((User) _, null) >> u

        when: "signup action is called"
        controller.signup()

        then: "the following view should be rendered with the proper model"
        view == '/index'
        model.user == u
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
    }

    void "signup - Service returns user with errors"() {
        given: "a user with errors"
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
    }

    void "signup - Success"() {
        given: "some mocked valid params"
        params.username = user.username
        params.email = user.email
        params.password = user.password
        params.password2 = user.password

        when: "signup action is called"
        controller.signup()

        then: "addUser method of UserManagementService should be called one time and returns the previous user"
        1 * userManagementService.addUser((User) _, RoleEnum.USER_ROLE.role) >> user

        then: "a message should be generated and the user should be redirected to the following url"
        response.redirectedUrl == '/login/auth'
        flash.message
    }

    void "update - Form requested"() {
        when: "calling update with method type != POST"
        Map map = (Map) controller.update()

        then: "the default update view should be rendered with the following model"
        map.user == user
        view == null
    }

    void "update - Empty params"() {
        given: "an empty user"
        User u = new User()
        u.validate()

        and: "request method type is POST"
        request.method = 'POST'

        and: "updateUser method returns the previous user"
        userManagementService.updateUser((User) _) >> u

        when: "update action is called"
        controller.update()

        then: "the following view should be rendered with the proper model"
        view == '/userManagement/update'
        model.user == u
    }

    void "update - Passwords are different"() {
        given: "some mocked params with two different passwords"
        params.username = "username"
        params.email = "email@email.com"
        params.password = "password"
        params.password2 = "differentPassword"

        and: "request method type is POST"
        request.method = 'POST'

        when: "update action is called"
        controller.update()

        then: "updateUser method of UserManagementService should not be called"
        0 * userManagementService.updateUser((User) _)

        then: "the following view should be rendered with the proper model"
        view == '/userManagement/update'
        model.user.username == params.username
        model.user.email == params.email
    }

    void "update - Service returns user with errors"() {
        given: "a user with errors"
        User u = new User()
        u.errors.rejectValue('email', null)

        and: "some mocked valid params"
        params.username = user.username
        params.email = user.email
        params.password = user.password
        params.password2 = user.password

        and: "request method type is POST"
        request.method = 'POST'

        and: "updateUser method of UserManagementService returns the previous user"
        userManagementService.updateUser((User) _) >> u

        when: "update action is called"
        controller.update()

        then: "the following view should be rendered with the proper model"
        view == '/userManagement/update'
        model.user.username == null
        model.user.email == null
    }

    void "update - Success"() {
        given: "some mocked valid params"
        params.username = "updatedUsername"
        params.email = "updatedEmail@email.com"
        params.password = "updatedPassword"
        params.password2 = "updatedPassword"

        and: "request method type is POST"
        request.method = 'POST'

        and: "updateUser method of UserManagementService returns the updated user"
        userManagementService.updateUser((User) _) >> user

        when: "update action is called"
        controller.update()

        then: "the updated user is reauthenticated with his updated username"
        1 * springSecurityService.reauthenticate((String) params.username)

        then: "a message should be generated and the user should be redirected to the following url"
        response.redirectedUrl == '/userManagement/update'
        flash.message
    }
}
