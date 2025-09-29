package com.example.project_verbal // Changed package to be imported

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quote(   // Quote class attributes for text views and
    val id: Long = 0L,
    val phrase: String,
    val meaning: String,
    val emotion: String,      // For basic filtering
    val certainty: String
) : Parcelable