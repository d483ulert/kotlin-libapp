package com.group.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book,Long> {

/*
    자바
    Optional<Book> findByName(String bookName);
*/
    fun findByName(bookName: String) : Book?
}