package com.smash

import com.smash.user.UserManagementService
import com.smash.user.Role
import com.smash.user.RoleEnum
import com.smash.user.User
import grails.transaction.Transactional
import groovy.sql.Sql

import javax.sql.DataSource

@Transactional
class BootstrapService {

    DataSource dataSource
    UserManagementService userManagementService

    Role adminRole
    Role userRole

    User userA
    User userB

    def initializeRoles() {
        Sql sql = new Sql(dataSource)
        adminRole = Role.findByAuthority(RoleEnum.ADMIN_ROLE.name())
        if (!adminRole) {
            sql.executeInsert("insert into role(id, authority) values (1,${RoleEnum.ADMIN_ROLE.name()})")
            adminRole = RoleEnum.ADMIN_ROLE.role
        }
        userRole = Role.findByAuthority(RoleEnum.USER_ROLE.name())
        if (!userRole) {
            sql.executeInsert("insert into role(id, authority) values (2,${RoleEnum.USER_ROLE.name()})")
            userRole = RoleEnum.USER_ROLE.role
        }
    }

    def inializeDevUsers() {
        userA = User.findByUsername("userA")
        if (!userA) {
            def user = new User(username: "userA", password: "1234", email: 'userA@smash.com')
            userA = userManagementService.addUser(user, userRole)
        }
        userB = User.findByUsername("userB")
        if (!userB) {
            def user = new User(username: "userB", password: "1234", email: 'userB@smash.com')
            userB = userManagementService.addUser(user, userRole)
        }
    }
}
