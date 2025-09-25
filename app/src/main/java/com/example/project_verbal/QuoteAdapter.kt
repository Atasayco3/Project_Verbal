// ui/QuoteAdapter.kt
package com.example.project_verbal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_verbal.Quote
import com.example.project_verbal.R

class QuoteAdapter( // Adapter to modify list and fragment views
    private val quotes: MutableList<Quote>,
    private val onClick: (Quote) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) { // Packaging the other attributes into the quote block visual
        private val phraseView: TextView = view.findViewById(R.id.tvPhrase)
        private val emotionView: TextView = view.findViewById(R.id.tvEmotion)

        fun bind(quote: Quote) {
            phraseView.text = quote.phrase
            emotionView.text = quote.emotion
            itemView.setOnClickListener { onClick(quote) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(quotes[position])
    }

    override fun getItemCount() = quotes.size // Count for the size of the list after each call

    fun addQuote(quote: Quote) { // Call when a quote is added to list
        quotes.add(quote)
        notifyItemInserted(quotes.size - 1)
    }
}
