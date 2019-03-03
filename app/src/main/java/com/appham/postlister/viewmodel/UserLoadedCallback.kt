package com.appham.postlister.viewmodel

import com.appham.postlister.model.data.User

interface UserLoadedCallback {

    fun setUser(user: User)

}
