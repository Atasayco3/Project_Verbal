// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.content.Context
import com.example.project_verbal.Quote
import java.io.File
import java.io.FileWriter
import java.util.Scanner
import kotlin.collections.forEach

object QuoteStore { // Creating a basic csv to store Quote class
    private const val FILE = "quotes.csv"

    fun load(context: Context): MutableList<Quote> {
        val file = File(context.filesDir, FILE).apply { createNewFile() } // Creates and a file even if not present
        val quotes = mutableListOf<Quote>()

        Scanner(file).use { scanner -> // Reads csv line by line
            while (scanner.hasNextLine()) {
                val parts = scanner.nextLine().split(",")
                if (parts.size >= 4) {
                    quotes += Quote(
                        phrase = parts[0],
                        meaning = parts[1],
                        emotion = parts[2],
                        certainty = parts[3]
                    )
                }
            }
        }
        return quotes
    }

    fun save(context: Context, quotes: List<Quote>) { // Saves contents to csv file
        val file = File(context.filesDir, FILE)
        FileWriter(file, false).use { writer ->
            quotes.forEach { q ->
                writer.write("${q.phrase},${q.meaning},${q.emotion},${q.certainty}\n")
            }
        }
    }

}