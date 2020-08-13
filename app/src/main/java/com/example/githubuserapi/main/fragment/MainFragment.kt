package com.example.githubuserapi.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.adapter.ListUserAdapter
import com.example.githubuserapi.viewModel.FollowersViewModel
import com.example.githubuserapi.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USERNAME = "extra_username"

        fun newInstance(index: Int): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: ListUserAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var index = 1
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }

        when (index) {
            1 -> {
                showListFollowers()
                getFollowersViewModel()
            }
            else -> {
                showListFollowers()
                getFollowingViewModel()
            }
        }
    }

    private fun showListFollowers() {
        adapter = ListUserAdapter()

        recycleView_followers.layoutManager = LinearLayoutManager(context)
        recycleView_followers.adapter = adapter
        recycleView_followers.setHasFixedSize(true)
    }

    private fun getFollowersViewModel() {
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)

        val username = activity?.intent?.getStringExtra(EXTRA_USERNAME)
        showLoading(true)
        followersViewModel.setFollowersUser(username!!)

        followersViewModel.getFollowersUser()
            .observe(viewLifecycleOwner, Observer { followersItem ->
                if (followersItem != null) {
                    adapter.setData(followersItem)
                    showLoading(false)
                }
            })
    }

    private fun getFollowingViewModel() {
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        val username = activity?.intent?.getStringExtra(EXTRA_USERNAME)
        followingViewModel.setFollowingUser(username!!)

        followingViewModel.getFollowingUser()
            .observe(viewLifecycleOwner, Observer { followingItem ->
                if (followingItem != null) {
                    adapter.setData(followingItem)
                    showLoading(false)
                }
            })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}