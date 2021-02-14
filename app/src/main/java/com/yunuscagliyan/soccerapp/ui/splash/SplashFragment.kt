package com.yunuscagliyan.soccerapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding:FragmentSplashBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSplashBinding.bind(view)
        navController=Navigation.findNavController(view)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            delay(4000L)
            binding.lottieLayout.cancelAnimation()
            navController.navigate(R.id.action_destination_splash_to_destination_home)
        }
    }

}