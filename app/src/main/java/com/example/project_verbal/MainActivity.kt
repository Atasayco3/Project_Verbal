// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_verbal.Quote
import com.example.project_verbal.R

import android.view.Menu
import android.view.MenuItem


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.drop_down_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("QuoteLIST", "options menu")
        when (item.itemId) {
            R.id.sortingAlg -> {
                Log.d("QuoteLIST", "Sorting algorithm")
                // movieList?.sortBy{ it?.rating }
                // quoteAdapter.notifyDataSetChanged()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}