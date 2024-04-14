package com.example.teamforge.controller

import com.example.teamforge.entity.TagEntity
import com.example.teamforge.repository.TagRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tag")
class TagController(
    val tagRepository: TagRepository,
) {

    @GetMapping("/all")
    fun getAllTags(): ResponseEntity<List<TagEntity>> {
        return ResponseEntity.ok(tagRepository.findAll())
    }

}