// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_verbal.Quote
import com.example.project_verbal.R

import android.view.Menu
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.project_verbal.AhoCorasick
import com.example.project_verbal.countFrequencies
import com.example.project_verbal.extractWords
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


private var keepsplash = true
class MainActivity : AppCompatActivity(),
    QuoteSelectionListener,
    QuoteEditListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {keepsplash}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch{
            delay(2000)
            keepsplash = false
        }


        if (savedInstanceState == null) { // Inflating main screen with list of quotes
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuoteListFragment())
                .commit()
        }
    }


    override fun onQuoteEdited(newQuote: Quote, position: Int) {

        // 1. Go back to the list screen
        supportFragmentManager.popBackStack()

        // 2. Wait for the pop to complete, then update the list
        supportFragmentManager.executePendingTransactions()

        val listFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (listFragment is QuoteListFragment) {
            listFragment.updateQuote(newQuote, position)
        }
    }





    private fun runEmotionInsights() {
        // 1) Load all quotes (reuse your CSV)
        val quotes = com.example.project_verbal.ui.QuoteStore.load(this)

        // 2) Concatenate all meanings (or do per-emotion later)
        val allMeaningText = quotes.joinToString(" ") { it.meaning }

        // 3) Extract words + filter stopwords via Aho-Corasick
        val words = extractWords(allMeaningText) // from your algo file
        val stopwords = listOf(
            "the","and","or","if","it","is","a","an","in","on","at",
            "to","of","for","with","as","that","this","be","are","was","were","by"
        )
        val ac = AhoCorasick(stopwords)
        val filtered = ac.filter(words)

        // 4) Frequency counts
        val freq = countFrequencies(filtered) // Map<String, Int>
        val top = freq.entries.sortedByDescending { it.value }.take(15)

        // 5) Show results quick (AlertDialog). You can replace with a fragment/Recycler later.
        val msg = buildString {
            appendLine("Top keywords in meanings (stopwords removed):")
            top.forEach { (w,c) -> appendLine("• $w — $c") }
        }

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Emotion Insights")
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onQuoteSelected(quote: Quote) {
        val listFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (listFragment is QuoteListFragment) {
            val pos = listFragment.getQuotePosition(quote)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuoteDetailFragment.newInstance(quote, pos))
                .addToBackStack(null)
                .commit()
        }
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
                runEmotionInsights() // <-- call our insights
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}