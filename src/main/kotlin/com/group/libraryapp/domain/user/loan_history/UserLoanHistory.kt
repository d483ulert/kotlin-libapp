package com.group.libraryapp.domain.user.loan_history

import com.group.libraryapp.domain.user.JavaUser
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

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
