package com.example.vocabularytrainer.data.remote



val validLoginResponse = """
    {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VycyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAiLCJleHAiOjE2ODk4NTM4MDAsInVzZXJJZCI6IjYyZDJmMjZjNWVjMTlkMGI5NDExYTg1MSJ9.AOst5xNHYDxsKBcRTr3H1F98JE1n_PBvxKV6tMIfSsg",
        "mainGroupId": "62d2f26c5ec19d0b9411a852"
    }
""".trimIndent()

val invalidLoginResponse = ""

val invalidEmailResponse = """
   {
    "successful": false,
    "message": "Incorrect username or password"
    }
""".trimIndent()

val invalidPasswordResponse = """
   {
    "successful": false,
    "message": "Incorrect username or password"
    }
""".trimIndent()