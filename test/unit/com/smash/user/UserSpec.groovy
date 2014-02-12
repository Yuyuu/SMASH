package com.smash.user

import spock.lang.Specification
import spock.lang.Unroll

class UserSpec extends Specification {

    @Unroll
    void "test constraints - email: #email"(String email, boolean expectedResult) {
        given: "a user"
        User user = new User(
                username: "username",
                email: email,
                password: "pass"
        )

        expect: "validate method to return the expected result"
        user.validate() == expectedResult

        where:
        email               | expectedResult
        "email"             | false
        "martin@email.com"  | true
        "email.com"         | false
    }

    @Unroll
    void "test constraints - username: #username"(String username, boolean expectedResult) {
        given: "a user"
        User user = new User(
                username: username,
                email: "email@email.com",
                password: "pass"
        )

        expect: "validate method to return the expected result"
        user.validate() == expectedResult

        where:
        username    | expectedResult
        "Username"  | true
        "User.name" | false
        "User_Name" | true
        "_Username" | false
        "Username_" | false
        "username"  | true
        "user@name" | false
    }
}
