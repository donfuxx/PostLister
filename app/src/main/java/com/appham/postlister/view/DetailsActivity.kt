package com.appham.postlister.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.appham.postlister.R
import com.appham.postlister.utils.replaceFragment

class DetailsActivity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel.userId = intent?.getIntExtra(KEY_USER_ID, 0) ?: 0
        viewModel.postTitle = intent?.getStringExtra(KEY_POST_TITLE) ?: ""
        viewModel.postBody = intent?.getStringExtra(KEY_POST_BODY) ?: ""

        supportActionBar?.title = viewModel.postTitle

        replaceFragment(DetailsFragment(), R.id.frameDetails)
    }

    companion object {

        private const val KEY_USER_ID = "KEY_USER_ID"
        private const val KEY_POST_TITLE = "KEY_POST_TITLE"
        private const val KEY_POST_BODY = "KEY_POST_BODY"

        fun newLaunchIntent(context: Context, userId: Int, postTitle: String, postBody: String): Intent {
            val extras = Bundle()
            extras.putInt(KEY_USER_ID, userId)
            extras.putString(KEY_POST_TITLE, postTitle)
            extras.putString(KEY_POST_BODY, postBody)
            return Intent(context, DetailsActivity::class.java).apply { putExtras(extras) }
        }
    }

}