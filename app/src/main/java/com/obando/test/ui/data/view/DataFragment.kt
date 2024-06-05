package com.obando.test.ui.data.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.obando.test.R
import com.obando.test.core.base.BaseFragment
import com.obando.test.core.models.WeatherModel
import com.obando.test.core.utils.JsonUtil
import com.obando.test.databinding.FragmentDataBinding

class DataFragment : BaseFragment() {

    private lateinit var binding: FragmentDataBinding

    private var weatherModel: WeatherModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDataBinding.bind(
            inflater.inflate(R.layout.fragment_data, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setObservers() {}

    override fun removeObservers() {}

    override fun initView() {
        val bundle = arguments
        weatherModel = JsonUtil.fromJsonToObject(
            bundle!!.getString("dataInfo").toString(),
            WeatherModel::class.java
        )
        initData()
    }

    override fun initData() {
        with(binding) {
            day1.text = weatherModel?.daily?.time?.get(0)
            day1Value.text = weatherModel?.daily?.temperature_2m_max?.get(0).toString().plus(" ").plus(weatherModel?.daily_units?.temperature_2m_max)
            day2.text = weatherModel?.daily?.time?.get(1)
            day2Value.text = weatherModel?.daily?.temperature_2m_max?.get(1).toString().plus(" ").plus(weatherModel?.daily_units?.temperature_2m_max)
            day3.text = weatherModel?.daily?.time?.get(2)
            day3Value.text = weatherModel?.daily?.temperature_2m_max?.get(2).toString().plus(" ").plus(weatherModel?.daily_units?.temperature_2m_max)
        }
    }
}