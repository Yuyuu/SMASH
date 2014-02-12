package com.smash.user

import spock.lang.Specification

class UserIntegrationSpec extends Specification {

    def springSecurityService

    void testBeforeInsert() {
        given: "a user"
        User user = new User(
                username: "username",
                email: "email@email.com",
                password: "password"
        )

        when: "saving the user"
        user.save(failOnError: true)

        then: "the password should be encoded"
        user.password == springSecurityService.encodePassword("password")
    }

    void testBeforeUpdate() {
        given: "two passwords for before and after update"
        String originalPassword = "originalPassword"
        String updatedPassword = "updatedPassword"

        and: "a user"
        User user = new User(
                username: "username",
                email: "email@email.com",
                password: originalPassword
        ).save(failOnError: true, flush: true)

        when: "updating the password and saving the user"
        user.password = updatedPassword
        user.save(failOnError: true, flush: true) // flush is required to fire beforeUpdate event

        then: "the password should be encoded"
        user.password != updatedPassword
    }
}
