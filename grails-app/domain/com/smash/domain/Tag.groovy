package com.smash.domain

class Tag {
	String name;

    static constraints = {
		name nullable: false, blank: false
    }
}
