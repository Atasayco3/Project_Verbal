package com.example.project_verbal.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.project_verbal.Quote
import com.example.project_verbal.R
import com.example.project_verbal.ui.QuoteEditListener



class QuoteDetailFragment : Fragment() {

    companion object {
        private const val ARG_QUOTE = "arg_quote"
        private const val ARG_POSITION = "arg_position"

        fun newInstance(q: Quote, position: Int) =
            QuoteDetailFragment().apply {
                arguments = bundleOf(
                    ARG_QUOTE to q,
                    ARG_POSITION to position
                )
            }
    }

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val updatedQuote = Quote(
                phrase = data.getStringExtra("phrase") ?: "",
                meaning = data.getStringExtra("meaning") ?: "",
                emotion = data.getStringExtra("emotion") ?: "",
                iscertain = data.getBooleanExtra("iscertain", false)
            )
            val pos = data.getIntExtra("position", -1)
            (activity as? QuoteEditListener)?.onQuoteEdited(updatedQuote, pos)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_quote_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val q = requireArguments().getParcelable<Quote>(ARG_QUOTE)!!
        val pos = requireArguments().getInt(ARG_POSITION)

        view.findViewById<TextView>(R.id.tvPhraseLarge).text = q.phrase
        view.findViewById<TextView>(R.id.tvMeaning).text = q.meaning
        view.findViewById<TextView>(R.id.tvEmotion).text = q.emotion
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox_certain_edit)
        checkBox.isChecked = q.iscertain


        val editBtn = view.findViewById<Button>(R.id.btnEdit)
        editBtn.setOnClickListener {
            val intent = Intent(requireContext(), AddQuoteActivity::class.java).apply {
                putExtra("phrase", q.phrase)
                putExtra("meaning", q.meaning)
                putExtra("emotion", q.emotion)
                putExtra("iscertain", checkBox.isChecked)
                putExtra("position", pos)
                putExtra("isEdit", true)
            }
            editLauncher.launch(intent)
        }
    }
}
