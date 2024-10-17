package com.example.codingchallenge.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.data.model.CARD_TYPE
import com.example.codingchallenge.data.model.CardItem
import com.example.codingchallenge.data.model.HomePageModel
import com.example.codingchallenge.data.repository.HomePageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TEXT = "text"
private const val TITLE_DESCRIPTION = "title_description"
private const val IMAGE_DESCRIPTION = "image_title_description"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomePageRepository
) : ViewModel() {
    private val _cards = MutableStateFlow<List<CardItem>>(mutableListOf())
    val cards: MutableStateFlow<List<CardItem>> get() = _cards

    fun fetchData() {
        viewModelScope.launch {
            Log.d("@@@", " fetch data 1")
            var result = repository.getData()
            parseCards(result.getOrNull())

            //     Log.d("@@@", " reseult  " + result.getOrNull()?.page?.cards?.size)
//            when(val result = repository.getData()) {
//                is result.is-> data.postValue(result.data)
//                is Result.frror -> error.postValue(result.exception.message)
//            }
        }
    }

    private fun parseCards(data: HomePageModel?) {
        data?.let {
            val cardList = mutableListOf<CardItem>()
            it.page.cards.forEach { card ->
                Log.d("@@@", " card type " + card.type)
                when (card.type) {
                    TEXT -> {
                        Log.d("@@@", " add here")
                        cardList.add(
                            CardItem(
                                type = CARD_TYPE.TEXT,
                                value = card.card.value,
                                fontSize = card.card.attributes?.font?.size,
                                fontColor = card.card.attributes?.color
                            )
                        )
                    }

                    TITLE_DESCRIPTION -> {
                        cardList.add(
                            CardItem(
                                type = CARD_TYPE.TITLE,
                                title = card.card.title,
                                description = card.card.description,
                            )
                        )
                    }

                    IMAGE_DESCRIPTION -> {
                        cardList.add(
                            CardItem(
                                type = CARD_TYPE.IMAGE_TITLE,
                                image = card.card.image,
                                title = card.card.title,
                                description = card.card.description,
                            )
                        )
                    }

                }
            }
            Log.d("@@@", " here cardlist size " + cardList.size)
            if (cardList.isNotEmpty()) {
                _cards.value = cardList
            }
        }
    }
}