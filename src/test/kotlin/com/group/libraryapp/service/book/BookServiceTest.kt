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
import com.group.libraryapp.dto.book.response.BookStatResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
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

    @Test
    @DisplayName("책 대여 권수를 정상확인")
    fun countLoanedBookTest(){
        //given
        val savedUser = userRepository.save(User("김지성",null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser,"A"),
            UserLoanHistory.fixture(savedUser,"B",UserLoanStatus.RETURN),
            UserLoanHistory.fixture(savedUser,"C",UserLoanStatus.RETURN)
        ))

        //when
        val result = bookService.countLoanedBook()

        //then
        assertThat(result).isEqualTo(1)
    }

    @Test
    @DisplayName("분야별 책 권수를 정상확인 한다.")
    fun getBookStatisticsTest() {
        //given
        bookRepository.saveAll(
            listOf(
                Book.fixture("A", BookType.COMPUTER),
                Book.fixture("B", BookType.COMPUTER),
                Book.fixture("A", BookType.SCIENCE),
            )
        )

        //vwhen
        val results = bookService.getBookStatistics()

        //then
        assertThat(results).hasSize(2)
        assertCount(results, BookType.COMPUTER,2)
        assertCount(results, BookType.SCIENCE,1)
    }

    private fun assertCount(results: List<BookStatResponse>, type:BookType,count: Int){
        assertThat(results.first{
            result -> result.type == type
        }.count
        ).isEqualTo(count)
    }
}