package com.example.project_verbal.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class AddQuoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_quote)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Id's for text inputs
        val phraseInput = findViewById<EditText>(R.id.etPhrase)
        val meaningInput = findViewById<EditText>(R.id.etMeaning)
        val emotionInput = findViewById<EditText>(R.id.etEmotion)
        val certaintyInput = findViewById<EditText>(R.id.etCertainty)

        // Id for button to save
        val saveButton = findViewById<Button>(R.id.btnSave)

        saveButton.setOnClickListener { // Funtion when button is clicked
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