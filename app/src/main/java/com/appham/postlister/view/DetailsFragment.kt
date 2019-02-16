package com.appham.postlister.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appham.postlister.R
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(activity!!).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtDetailsPostBody.text = viewModel.postBody

        viewModel.getIsSuccess().observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                txtDetailsUser.text = it.name
            }
        })

        viewModel.loadPostDetails()


    }
}
