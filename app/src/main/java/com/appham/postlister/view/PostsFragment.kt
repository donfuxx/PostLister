package com.appham.postlister.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.appham.postlister.R
import com.appham.postlister.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    private val postsAdapter by lazy {
        PostsAdapter(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView for users list
        listPosts.setHasFixedSize(true)
        listPosts.adapter = postsAdapter
        listPosts.layoutManager = LinearLayoutManager(activity)
        listPosts.itemAnimator = null

        // LiveData Observers
        viewModel.getIsBusy().observe(viewLifecycleOwner, Observer { isBusy ->
            progressBarPosts.visibility = if (isBusy) View.VISIBLE else View.GONE
        })
        viewModel.getLoadedPosts().observe(viewLifecycleOwner, Observer { items ->
            Log.d(javaClass.simpleName, "observed: $items")
            items?.let {
                postsAdapter.submitList(items)
            }
        })

        viewModel.getPosts()
    }

}
