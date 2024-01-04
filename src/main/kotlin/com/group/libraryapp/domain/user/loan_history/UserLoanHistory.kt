package com.group.libraryapp.domain.user.loan_history

import com.group.libraryapp.domain.user.User
import javax.persistence.*

@Entity
class UserLoanHistory (

    val bookName: String,
    var isReturn: Boolean,

    @ManyToOne
    val user: User,


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null

){
    fun doReturn(){
        this.isReturn=true
    }
}
