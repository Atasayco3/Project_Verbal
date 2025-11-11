// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Newly added imports
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import com.example.project_verbal.R


class AddQuoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Project_Verbal)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quote)


        // Id's for text inputs
        val phraseInput = findViewById<EditText>(R.id.etPhrase)
        val meaningInput = findViewById<EditText>(R.id.etMeaning)
        val emotionInput = findViewById<EditText>(R.id.etEmotion)
        val certaintyInput = findViewById<EditText>(R.id.etCertainty)

        // Id for button to save
        val saveButton = findViewById<Button>(R.id.btnSave)

        saveButton.setOnClickListener { // Function when button is clicked
            val intent = Intent().apply {
                putExtra("phrase", phraseInput.text.toString())
                putExtra("meaning", meaningInput.text.toString())
                putExtra("emotion", emotionInput.text.toString())
                putExtra("certainty", certaintyInput.text.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}