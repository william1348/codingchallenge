package com.example.codingchallenge.data.model

// actual usable card object after parsing API response
data class CardItem(
    var type: CARD_TYPE,
    var value: String? = null,
    var title: Title? = null,
    var description: Description? = null,
    var image: Image? = null,
    var fontColor: String? = null,
    var fontSize: Int? = null
)