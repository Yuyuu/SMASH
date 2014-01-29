package com.smash

import com.smash.user.Role
import com.smash.user.RoleEnum
import com.smash.user.User
import spock.lang.Specification

class BootstrapServiceIntegrationSpec extends Specification {

    BootstrapService bootstrapService

    def testInitializeRoles() {
        when: "calling initializeRoles"
        bootstrapService.initializeRoles()

        then: "two role should be saved in the datastore"
        Role.findByAuthority(RoleEnum.USER_ROLE.name())
        Role.findByAuthority(RoleEnum.ADMIN_ROLE.name())
    }

    def testInitializeDevUsers() {
        when: "calling initializeDevUsers"
        bootstrapService.inializeDevUsers()

        then: "two users should be saved in the datastore"
        User.findAll().size() == 2
    }
}
