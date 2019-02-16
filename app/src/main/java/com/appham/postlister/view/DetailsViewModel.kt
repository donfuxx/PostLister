package com.appham.postlister.view

import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

    var userId: Int = 0
    lateinit var postTitle: String
    lateinit var postBody: String

    fun loadPostDetails() {

    }


}
