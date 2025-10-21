// ui/QuoteListFragment.kt
package com.example.project_verbal.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_verbal.Quote
import com.example.project_verbal.R
//import com.example.project_verbal.data.QuoteStore
import com.example.project_verbal.ui.QuoteStore
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface QuoteSelectionListener {
    fun onQuoteSelected(quote: Quote)
}

class QuoteListFragment : Fragment() {
    private lateinit var listener: QuoteSelectionListener
    private lateinit var adapter: QuoteAdapter
    private val quotes = mutableListOf<Quote>()

    override fun onAttach(context: Context) { // Attaching list fragment into the main page
        super.onAttach(context)
        listener = context as? QuoteSelectionListener
            ?: throw RuntimeException("Host must implement QuoteSelectionListener")
    }

    override fun onCreateView( // Inflates view with list fragment
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quote_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //
        val rv = view.findViewById<RecyclerView>(R.id.rvQuotes)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = QuoteAdapter(quotes) { listener.onQuoteSelected(it) }
        rv.adapter = adapter

        androidx.recyclerview.widget.ItemTouchHelper(adapter.swipeToDeleteCallback)
            .attachToRecyclerView(rv)

        quotes.clear()
        quotes.addAll(QuoteStore.load(requireContext()))
        adapter.notifyDataSetChanged()

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            addLauncher.launch(Intent(requireContext(), AddQuoteActivity::class.java)) // Sends packaged intent back to
        }
    }

    private val addLauncher = registerForActivityResult( // Packaged data to add to list
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data ?: return@registerForActivityResult
        val q = Quote(
            phrase = data.getStringExtra("phrase") ?: "",
            meaning = data.getStringExtra("meaning") ?: "",
            emotion = data.getStringExtra("emotion") ?: "",
            certainty = data.getStringExtra("certainty") ?: ""
        )
        quotes.add(q)
        adapter.notifyItemInserted(quotes.lastIndex)
        QuoteStore.save(requireContext(), quotes)
    }
}
