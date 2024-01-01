package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
){


    @AfterEach
    fun clear(){
        userRepository.deleteAll()
    }

    @Test
    fun saveUserTest(){
        //given
        val request = UserCreateRequest("김지성",null)

        //when
        userService.saveUser(request);

        //then
        val results = userRepository.findAll();
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("김지성")
        assertThat(results[0].age).isNull()
    }

    @Test
    fun getUsersTest(){
        //given
        userRepository.saveAll(listOf(
            User("A",20),
            User("B",null)
        ))

        //when
        val results = userService.getUsers()

        //then

        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A","B")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20,null)
    }

    @Test
    fun updateUserNameTest(){
        //given
        val saveUser = userRepository.save(User("A",null))
        val request = UserUpdateRequest(saveUser.id, "B")

        //when
        userService.updateUserName(request)

        //then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")

    }

    @Test
    fun deleteUserTest(){
        //given
        val saveUser = userRepository.save(User("A",null))

        //when
        userService.deleteUser("A")

        //then
        assertThat(userRepository.findAll()).hasSize(0)
    }

}