package com.group.libraryapp.controller

import com.group.libraryapp.dto.metro.MetroRequest
import com.group.libraryapp.service.metro.MetroService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/metro")
class MetroController (
    private val metroService: MetroService
){

    @PostMapping("/")
    fun list(@RequestBody request: MetroRequest){
        metroService.list(request);
    }
}