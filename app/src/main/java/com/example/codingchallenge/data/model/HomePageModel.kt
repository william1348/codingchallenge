package com.example.codingchallenge.data.model

import com.google.gson.annotations.SerializedName

data class HomePageModel(
    var page: HomePageWrapper
)

data class HomePageWrapper(
    var cards: List<CardWrapper>
)

data class CardWrapper(
    @SerializedName("card_type") val type: String,
    var card: Card
)

data class Card(
    var value: String,
    var attributes: Attributes?,
    var title: Title?,
    var description: Description?,
    var image: Image?
)

data class Title(
    var value: String,
    var attributes: Attributes
)

data class Description(
    var value: String,
    var attributes: Attributes,
    var font: Font
)

data class Attributes(
    @SerializedName("text_color") val color: String?,
    var font: Font
)

data class Font(
    var size: Int
)

data class Image(
    var url: String,
    var size: Size
)

data class Size(
    var width: Int,
    var height: Int
)

enum class CARD_TYPE {
    TEXT, TITLE, IMAGE_TITLE
}
