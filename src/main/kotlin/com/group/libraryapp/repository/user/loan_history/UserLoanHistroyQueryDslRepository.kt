package com.group.libraryapp.repository.user.loan_history

import com.group.libraryapp.domain.user.loan_history.QUserLoanHistory.userLoanHistory
import com.group.libraryapp.domain.user.loan_history.UserLoanHistory
import com.group.libraryapp.domain.user.loan_history.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserLoanHistroyQueryDslRepository(
    private val queryFcatory: JPAQueryFactory,
){
    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory?{
        return queryFcatory.select(userLoanHistory)
            .from(userLoanHistory)
            .where(
                userLoanHistory.bookName.eq((bookName)),
                status?.let { userLoanHistory.status.eq(status) } // status가 null일 경우 무시하게됨.
            )
            .limit(1)
            .fetchOne()
    }

    fun count(status: UserLoanStatus): Long{
        return queryFcatory.select(userLoanHistory.id.count())
            .from(userLoanHistory)
            .where(userLoanHistory.status.eq(status))
            .fetchOne() ?: 0L   // null일 경우 0L
    }
}