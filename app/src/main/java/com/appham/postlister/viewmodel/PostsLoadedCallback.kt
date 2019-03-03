package com.appham.postlister.viewmodel

import com.appham.postlister.model.data.Post

interface PostsLoadedCallback {

    fun setPosts(posts: List<Post>)
}
