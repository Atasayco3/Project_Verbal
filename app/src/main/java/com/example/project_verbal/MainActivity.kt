// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_verbal.Quote
import com.example.project_verbal.R


class MainActivity : AppCompatActivity(), QuoteSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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