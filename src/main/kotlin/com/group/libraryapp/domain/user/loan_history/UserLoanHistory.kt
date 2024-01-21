package com.group.libraryapp.domain.user.loan_history

import com.group.libraryapp.domain.user.User
import javax.persistence.*

@Entity
class UserLoanHistory (

    val bookName: String,
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @ManyToOne
    val user: User,


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null

){
    val isReturn : Boolean
        get() = this.status == UserLoanStatus.RETURN

    fun doReturn(){
        this.status=UserLoanStatus.RETURN
    }

    companion object{
        fun fixture(
            user: User,
            bookName: String = "이상한 나라의 엘리스",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null,
        ): UserLoanHistory{
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status
            )
        }
    }
}
