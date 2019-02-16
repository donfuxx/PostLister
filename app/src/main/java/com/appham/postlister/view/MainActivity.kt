package com.appham.postlister.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appham.postlister.R
import com.appham.postlister.utils.replaceFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(PostsFragment(), R.id.framePosts)

        viewModel.getNextScreen().observe(this, Observer { post ->
            post?.let {
                startActivity(DetailsActivity.newLaunchIntent(this, post.id, post.userId, post.title, post.body))
            }
        })
    }


}
