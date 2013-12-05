package com.smash.domain

class Tag {
	String name;

    static constraints = {
		name blank: false
		name nullable: false
    }
}
