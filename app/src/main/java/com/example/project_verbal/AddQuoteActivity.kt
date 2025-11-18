package com.example.project_verbal.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.project_verbal.R

class AddQuoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Project_Verbal)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quote)

        val phraseInput = findViewById<EditText>(R.id.etPhrase)
        val meaningInput = findViewById<EditText>(R.id.etMeaning)
        val emotionInput = findViewById<EditText>(R.id.etEmotion)
        val certaintyInput = findViewById<EditText>(R.id.etCertainty)
        val saveButton = findViewById<Button>(R.id.btnSave)

        val isEdit = intent.getBooleanExtra("isEdit", false)
        val position = intent.getIntExtra("position", -1)

        if (isEdit) {
            phraseInput.setText(intent.getStringExtra("phrase"))
            meaningInput.setText(intent.getStringExtra("meaning"))
            emotionInput.setText(intent.getStringExtra("emotion"))
            certaintyInput.setText(intent.getStringExtra("certainty"))
            saveButton.text = "Save Changes"
        }

        saveButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("phrase", phraseInput.text.toString())
                putExtra("meaning", meaningInput.text.toString())
                putExtra("emotion", emotionInput.text.toString())
                putExtra("certainty", certaintyInput.text.toString())
                if (isEdit) putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
