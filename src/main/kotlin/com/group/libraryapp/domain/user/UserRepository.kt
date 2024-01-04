package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User,Long> {

/*
    자바
    Optional<User> findByName(String name);
*/

    fun findByName(name: String?) : User

}