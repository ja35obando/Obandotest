package com.obando.test.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.obando.test.R
import com.obando.test.core.base.BaseFragment
import com.obando.test.core.extensions.hide
import com.obando.test.core.extensions.show
import com.obando.test.core.models.WeatherModel
import com.obando.test.core.utils.JsonUtil
import com.obando.test.databinding.FragmentHomeBinding
import com.obando.test.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private var weatherModel: WeatherModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.bind(
            inflater.inflate(R.layout.fragment_home, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    override fun setObservers() {
        with(homeViewModel) {
            summary.observe(viewLifecycleOwner) {
                displayLoadingView(display = false)
                if (it != null) {
                    weatherModel = it
                    loadInformation()
                } else {
                    showError()
                }
            }
        }
    }

    override fun removeObservers() {
        with(homeViewModel) {
            summary.removeObservers(viewLifecycleOwner)
        }
    }

    override fun initView() {
        binding.containerData.setOnClickListener {
            val args = Bundle()
            args.putString("dataInfo", JsonUtil.fromObjectToJson(weatherModel!!))
            findNavController().navigate(R.id.dataFragment, args)
        }
    }

    override fun initData() {
        displayLoadingView(display = true)
        homeViewModel.getSummary()
    }

    private fun loadInformation() {
        with(binding) {
            errorMessage.hide()
            containerData.show()
            latitudeValue.text = weatherModel?.latitude.toString()
            longitudeValue.text = weatherModel?.longitude.toString()
            timezoneValue.text = weatherModel?.timezone.plus(" ").plus(weatherModel?.timezone_abbreviation)
            dailyValue.text= weatherModel?.current?.temperature_2m.plus(" ").plus(weatherModel?.daily_units?.temperature_2m_max)
        }
    }

    private fun showError() {
        with(binding) {
            errorMessage.show()
            containerData.hide()
        }
    }
}