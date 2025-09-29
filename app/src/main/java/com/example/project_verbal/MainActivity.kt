// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Context
import com.example.project_verbal.Quote
import java.io.File
import java.io.FileWriter
import java.util.Scanner




class MainActivity : AppCompatActivity(), QuoteSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) { // Inflating main screen with list of quotes
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuoteListFragment())
                .commit()
        }
    }

    override fun onQuoteSelected(quote: Quote) { // Clicking on a quote and bringing up that quotes fragment for display.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, QuoteDetailFragment.newInstance(quote))
            .addToBackStack(null)
            .commit()
    }
}