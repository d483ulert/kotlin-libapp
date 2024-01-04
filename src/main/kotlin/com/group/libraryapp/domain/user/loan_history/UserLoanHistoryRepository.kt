package com.group.libraryapp.domain.user.loan_history

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository: JpaRepository<UserLoanHistory,Long> {

/*
    자바
    UserLoanHistory findByBookNameAndIsReturn(String bookName, boolean isReturn);
*/

    fun findByBookNameAndIsReturn(bookName: String, isReturn: Boolean): UserLoanHistory?
}