package com.yunuscagliyan.soccerapp.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunuscagliyan.soccerapp.MainActivity
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.databinding.FragmentHomeBinding
import com.yunuscagliyan.soccerapp.extension.onQueryTextChanged
import com.yunuscagliyan.soccerapp.utils.Status
import com.yunuscagliyan.soccerapp.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var teamAdapter: TeamAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        teamAdapter = TeamAdapter()

        setupToolbar()
        setupRVTeam()
        subscribeToObservers()

        setHasOptionsMenu(true)
    }

    private fun setupToolbar() {
        (activity as? MainActivity)?.setUpToolbar(binding.toolbar)
    }

    private fun setupRVTeam() {
        binding.rvTeam.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = teamAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.menu_fragment_home,menu)
        val searchItem=menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged { searchText->
            lifecycleScope.launch {
                delay(5000L)
                homeViewModel.changeSearchQuery(searchText)
            }
        }
    }

    private fun subscribeToObservers() {
        homeViewModel.teamList.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    res.data?.let { teamList ->
                        teamAdapter.submitTeamList(teamList)
                    }
                    binding.apply {
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        rvTeam.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        shimmerLayout.startShimmer()
                        shimmerLayout.visibility = View.VISIBLE
                        rvTeam.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    Timber.e(res.message)
                    binding.apply {
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        rvTeam.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}