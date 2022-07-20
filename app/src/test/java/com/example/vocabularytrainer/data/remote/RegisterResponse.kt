package com.example.vocabularytrainer.data.remote

val validRegisterResponse = """
    "successful": true,
    "message": "Successfully created account"
""".trimIndent()

val invalidRegisterResponse = """
    
""".trimIndent()

val invalidUserAlreadyExistsResponse = """
    {
        "successful": false,
        "message": "A user with this email already exists"
    }
""".trimIndent()


val invalidNotValidEmailResponse = """
    {
        "successful": false,
        "message": "That's not a valid email"
    }
""".trimIndent()

val invalidShortPasswordResponse = """
    {
        "successful": false,
        "message": "Password is too short"
    }
""".trimIndent()