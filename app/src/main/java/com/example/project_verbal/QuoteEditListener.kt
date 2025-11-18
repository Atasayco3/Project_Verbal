package com.example.project_verbal.ui

import com.example.project_verbal.Quote

interface QuoteEditListener {
    fun onQuoteEdited(updatedQuote: Quote, position: Int)
}
