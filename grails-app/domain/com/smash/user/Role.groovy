package com.smash.user

class Role {

	String authority

	static mapping = {
		cache true
        version(false)
	}

	static constraints = {
		authority blank: false, unique: true, inList: RoleEnum.values()*.name()
	}
}

enum RoleEnum {
    ADMIN_ROLE(1),
    USER_ROLE(2)

    Long id

    RoleEnum(Long id) {
        this.id = id
    }

    Role getRole() {
        Role.get(id)
    }
}
