package com.yunuscagliyan.soccerapp.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.yunuscagliyan.soccerapp.MainActivity
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.databinding.FragmentHomeBinding
import com.yunuscagliyan.soccerapp.extension.onQueryTextChanged
import com.yunuscagliyan.soccerapp.utils.Status
import com.yunuscagliyan.soccerapp.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialContainerTransform()
        reenterTransition = MaterialContainerTransform()
        //exitTransition = Hold()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        teamAdapter = TeamAdapter()

        setupToolbar()
        setupRVTeam()
        subscribeToObservers()

        setHasOptionsMenu(true)
        setupFab()
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

    private fun setupFab() {
        binding.fabFixture.setOnClickListener {
            homeViewModel.fixtureFabClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_home, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged { searchText ->
            viewLifecycleOwner.lifecycleScope.launch {
                delay(5000L)
                homeViewModel.changeSearchQuery(searchText)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_setting -> {
                homeViewModel.settingMenuClick()
                navController.navigate(R.id.action_setting)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
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

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.homeEventChannel.collect { event ->
                when (event) {
                    is HomeViewModel.HomeEvent.NavigateFixtureScreen -> {
                        val extras = FragmentNavigatorExtras(binding.fabFixture to "shared_element")
                        navController.navigate(R.id.action_fixture, null, null, extras)
                    }
                    is HomeViewModel.HomeEvent.NavigateSettingScreen -> {
                        //navController.navigate(R.id.action_setting)
                    }
                    else -> Unit
                }
            }
        }
    }

}