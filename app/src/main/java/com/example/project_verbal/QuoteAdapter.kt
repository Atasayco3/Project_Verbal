package com.example.project_verbal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.project_verbal.Quote
import com.example.project_verbal.R
import android.graphics.Color


class QuoteAdapter(
    private val quotes: MutableList<Quote>,
    private val onClick: (Quote) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val phraseView: TextView = view.findViewById(R.id.tvPhrase)
        private val emotionView: TextView = view.findViewById(R.id.tvEmotion)

        fun bind(quote: Quote) {
            phraseView.text = quote.phrase
            emotionView.text = quote.emotion

            if (quote.iscertain) {
                // CERTAIN → blue background, white text
                val blue = Color.parseColor("#2196F3")
                phraseView.setBackgroundColor(blue)
                emotionView.setBackgroundColor(blue)
                phraseView.setTextColor(Color.WHITE)
                emotionView.setTextColor(Color.WHITE)
            } else {
                // NOT CERTAIN → yellow background, black text
                val yellow = Color.parseColor("#FFFF00")
                phraseView.setBackgroundColor(yellow)
                emotionView.setBackgroundColor(yellow)
                phraseView.setTextColor(Color.BLACK)
                emotionView.setTextColor(Color.BLACK)
            }

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

    override fun getItemCount() = quotes.size

    fun addQuote(quote: Quote) {
        quotes.add(quote)
        notifyItemInserted(quotes.size - 1)
    }

    fun removeItem(position: Int) {
        quotes.removeAt(position)
        notifyItemRemoved(position)
    }

    val swipeToDeleteCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            removeItem(viewHolder.adapterPosition)
        }
    }
}
