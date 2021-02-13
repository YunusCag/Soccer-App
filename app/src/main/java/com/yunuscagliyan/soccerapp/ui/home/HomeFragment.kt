package com.yunuscagliyan.soccerapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.databinding.FragmentHomeBinding
import com.yunuscagliyan.soccerapp.utils.Status
import com.yunuscagliyan.soccerapp.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var teamAdapter: TeamAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        homeViewModel.getTeamList()

        teamAdapter = TeamAdapter()
        setUpRVTeam()
        subscribeToObservers()
    }

    private fun setUpRVTeam() {
        binding.rvTeam.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = teamAdapter
        }
    }

    private fun subscribeToObservers() {
        homeViewModel.teamList.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    res.data?.let { teamList ->
                        teamAdapter.submitList(teamList)
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