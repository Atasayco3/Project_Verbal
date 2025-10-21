package com.example.project_verbal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.project_verbal.Quote
import com.example.project_verbal.R

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
