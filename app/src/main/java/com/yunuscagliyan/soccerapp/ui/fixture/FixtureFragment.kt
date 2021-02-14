package com.yunuscagliyan.soccerapp.ui.fixture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialContainerTransform
import com.yunuscagliyan.soccerapp.MainActivity
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.databinding.FragmentFixtureBinding
import com.yunuscagliyan.soccerapp.utils.Status
import com.yunuscagliyan.soccerapp.viewmodels.FixtureViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FixtureFragment : Fragment(R.layout.fragment_fixture) {

    private lateinit var binding: FragmentFixtureBinding
    private val fixtureViewModel: FixtureViewModel by viewModels()
    private lateinit var fixtureAdapter: FixturePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFixtureBinding.bind(view)
        fixtureAdapter = FixturePageAdapter()

        setupToolbar()
        fixtureViewModel.getFixtures()
        setupRVFixture()
        subscribeObservers()
    }

    private fun setupToolbar() {
        (activity as? MainActivity)?.setUpToolbar(binding.toolbar)
    }

    private fun setupRVFixture() {
        binding.vpFixture.adapter = fixtureAdapter
    }

    private fun subscribeObservers() {
        fixtureViewModel.fixtures.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    res.data?.let { fixtureList ->
                        fixtureAdapter.submitFixtureList(fixtureList)
                        binding.apply {
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            shimmerCard.visibility = View.GONE
                            vpFixture.visibility = View.VISIBLE
                        }
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        shimmerLayout.startShimmer()
                        shimmerLayout.visibility = View.VISIBLE
                        shimmerCard.visibility = View.VISIBLE
                        vpFixture.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    Timber.e(res.message)
                    binding.apply {
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        shimmerCard.visibility = View.GONE
                        vpFixture.visibility = View.GONE
                    }
                }
            }
        }
    }


}