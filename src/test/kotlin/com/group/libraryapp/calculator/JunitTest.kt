package com.group.libraryapp.calculator

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JunitTest {
// BeforeAll -> @BeforeEach -> @Test -> @AfterEach -> @AfterAll
    companion object{
        @JvmStatic
        @BeforeAll
        fun beforeAll(){
            print("모든 테스트 시작 전")
        }

        @JvmStatic
        @AfterAll
        fun afterAll(){
            print("모든 테스트 종료 후")
        }

    }

    @BeforeEach
    fun beforeEach(){
        println("각 테스트 시작 전")
    }

    @AfterEach
    fun afterEach(){
        println("각 테스트 종료 후")
    }

    @Test
    fun test1(){
        println("후후 1")
    }

    @Test
    fun test2(){
        println("후후 2")
    }
}