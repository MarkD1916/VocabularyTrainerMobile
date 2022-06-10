package com.example.vocabularytrainer.data.remote.auth.request

import com.example.vocabularytrainer.R
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.nio.channels.AsynchronousFileChannel.open
import javax.xml.parsers.DocumentBuilderFactory


data class AccountRequest(
    @SerializedName("username") val email: String,
    val password: String
)
