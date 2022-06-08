package com.example.vocabularytrainer.auth.use_case

import com.example.vocabularytrainer.domain.auth.use_case.ValidatePassword
import org.junit.Before

class ValidatePasswordTest {
    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp(){
        validatePassword = ValidatePassword()
    }
}