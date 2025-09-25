// ui/QuoteDetailFragment.kt
package com.example.project_verbal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.project_verbal.Quote
import com.example.project_verbal.R

class QuoteDetailFragment : Fragment() { // Fragment class
    companion object {
        private const val ARG_QUOTE = "arg_quote"
        fun newInstance(q: Quote) = QuoteDetailFragment().apply {
            arguments = bundleOf(ARG_QUOTE to q)
        }
    }

    override fun onCreateView( // Inflates fragment with detailed
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quote_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Creating documented fragment of details
        val q = requireArguments().getParcelable<Quote>(ARG_QUOTE)!!
        view.findViewById<TextView>(R.id.tvPhraseLarge).text = q.phrase
        view.findViewById<TextView>(R.id.tvMeaning).text = q.meaning
        view.findViewById<TextView>(R.id.tvEmotion).text = q.emotion
        view.findViewById<TextView>(R.id.tvCertainty).text = q.certainty
    }
}
