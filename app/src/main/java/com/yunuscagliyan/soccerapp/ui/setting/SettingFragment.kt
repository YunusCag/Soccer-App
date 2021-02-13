package com.yunuscagliyan.soccerapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yunuscagliyan.soccerapp.MainActivity
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.databinding.FragmentSettingBinding
import com.yunuscagliyan.soccerapp.viewmodels.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        setupToolbar()
        subscribeObserver()
        setupNightMode()
    }

    private fun setupToolbar() {
        (activity as? MainActivity)?.setUpToolbar(binding.toolbar)
    }

    private fun subscribeObserver() {
        settingViewModel.screenModePreference.observe(viewLifecycleOwner){screenMode->
            binding.switchCompat.isChecked=screenMode.isNightMode
        }
    }

    private fun setupNightMode() {
        binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.updateIsNightMode(isChecked)
        }
    }


}