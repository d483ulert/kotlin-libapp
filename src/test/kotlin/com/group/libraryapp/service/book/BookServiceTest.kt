package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loan_history.UserLoanHistory
import com.group.libraryapp.domain.user.loan_history.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loan_history.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
){

    @AfterEach
    fun clear(){
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun saveBookTest(){
        //given
        val requestBook = BookRequest("이상한 나라의 엘리스", BookType.COMPUTER)

        //when
        bookService.saveBook(requestBook)

        //then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("이상한 나라의 엘리스")
        assertThat(books[0].type).isEqualTo(BookType.COMPUTER)

    }

    @Test
    fun 책대출정상(){
        //given
        bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
        userRepository.save(User("홍길동", 10))
        val request= BookLoanRequest("홍길동","이상한 나라의 엘리스")

        //when
        bookService.loanBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("이상한 나라의 엘리스")
        assertThat(results[0].user.name).isEqualTo("홍길동")
        assertThat(results[0].status).isEqualTo(UserLoanStatus.LOANED)

    }

    @Test
    fun 책대출exception(){
        //given
        bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
        val user  = userRepository.save(User("홍길동", 10))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(user,"이상한 나라의 엘리스"))


        val request= BookLoanRequest("홍길동","이상한 나라의 엘리스")


        //when & then
        val msg = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message

        assertThat(msg).isEqualTo("진작 대출되어 있는 책입니다.")
    }


    @Test
    fun 책반납(){
        //given
        bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
        val user  = userRepository.save(User("홍길동", 10))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(user,"이상한 나라의 엘리스"))
        val request = BookReturnRequest("홍길동","이상한 나라의 엘리스")

        //when
        bookService.returnBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("이상한 나라의 엘리스")
        assertThat(results[0].status).isEqualTo(UserLoanStatus.RETURN)

    }
}