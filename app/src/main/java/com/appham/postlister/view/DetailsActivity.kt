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

        val userId = intent.getIntExtra(KEY_USER_ID, 0)

        viewModel.setUserId(userId)
        supportActionBar?.title = "User $userId"

        replaceFragment(DetailsFragment(), R.id.frameDetails)
    }

    companion object {

        private const val KEY_USER_ID = "KEY_USER_ID"

        fun newLaunchIntent(context: Context, userId: Int): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.extras?.putInt(KEY_USER_ID, userId)
            return intent
        }
    }

}