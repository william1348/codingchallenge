package com.example.codingchallenge.data.model

// usable card object after parsing API response
data class CardItem(
    var type: CARD_TYPE, // text, title, or image
    var value: String? = null, // non-null for text
    var title: Title? = null, // non-null for title or image
    var description: Description? = null, // non-null for title or image
    var image: Image? = null, // non-null for image only
    var fontColor: String? = null, // text only
    var fontSize: Int? = null // text only
)