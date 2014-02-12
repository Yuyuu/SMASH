package com.smash

import com.smash.user.User
import grails.transaction.Transactional

@Transactional
class BootstrapTestService {

    def initializeTests() {
        initializeUsers()
    }

    def initializeUsers() {
        new User(username: "owner", email: "owner@ups.com", password: "pwd",
                enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save()
        new User(username: "owner2", email: "owner2@ups.com", password: "pwd",
                enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save()
    }
}