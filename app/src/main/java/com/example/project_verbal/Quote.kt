package com.example.project_verbal // Changed package to be imported

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quote(   // Quote class attributes for text views
    val id: Long = 0L,
    val phrase: String,
    val meaning: String,
    val emotion: String,
    val iscertain: Boolean = false

) : Parcelable