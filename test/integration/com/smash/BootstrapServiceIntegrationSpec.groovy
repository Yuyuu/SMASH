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

        then: "two roles should be created"
        bootstrapService.adminRole
        bootstrapService.userRole

        and: "saved in the datastore"
        Role.findByAuthority(RoleEnum.USER_ROLE.name())
        Role.findByAuthority(RoleEnum.ADMIN_ROLE.name())
    }

    def testInitializeDevUsers() {
        when: "calling initializeDevUsers"
        bootstrapService.inializeDevUsers()

        then: "two users should be created"
        bootstrapService.userA
        bootstrapService.userB

        and: "saved in the datastore"
        User.findWhere(username: bootstrapService.userA.username)
        User.findWhere(username: bootstrapService.userB.username)
    }
}
