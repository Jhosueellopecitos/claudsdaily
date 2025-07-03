package com.puce.claudsdaily.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@ControllerAdvice
class GlobalExceptionHandler {

    data class ErrorResponse(val status: Int, val error: String, val message: String?)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException) =
        buildResponse(NOT_FOUND, ex.message)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException) =
        buildResponse(BAD_REQUEST, ex.bindingResult.allErrors.first().defaultMessage)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraint(ex: ConstraintViolationException) =
        buildResponse(BAD_REQUEST, ex.message)

    @ExceptionHandler(Exception::class)
    fun handleOther(ex: Exception) =
        buildResponse(INTERNAL_SERVER_ERROR, ex.message)

    private fun buildResponse(status: HttpStatus, msg: String?) =
        ResponseEntity(ErrorResponse(status.value(), status.reasonPhrase, msg), status)
}
