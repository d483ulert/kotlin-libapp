package com.group.libraryapp.domain.user.loan_history

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository: JpaRepository<UserLoanHistory,Long> {

    fun findByBookNameAndStatus(bookName: String, Status: UserLoanStatus): UserLoanHistory?

    fun countByStatus(status: UserLoanStatus): Long
}