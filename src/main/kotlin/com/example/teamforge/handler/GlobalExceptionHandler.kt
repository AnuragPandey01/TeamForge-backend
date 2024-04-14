package com.example.teamforge.handler

import com.example.teamforge.util.ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.method.MethodValidationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.NoSuchElementException

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler(){

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val error = ex.bindingResult.fieldErrors.first().defaultMessage
        return ResponseEntity.badRequest().body(ApiResponse(false, error ?: "Validation Error", null))
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ApiResponse>{
        return ResponseEntity.badRequest().body(ApiResponse(false, e.message ?: "No such element", null))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse>{
        return ResponseEntity.badRequest().body(ApiResponse(false, e.message ?: "An error occurred", null))
    }

}
