package com.obando.test.core.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.obando.test.R
import com.obando.test.core.extensions.hide
import com.obando.test.core.extensions.show

abstract class SimpleBaseFragment : Fragment() {

    private lateinit var commonViewLoading: ConstraintLayout
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseViewComponents()
    }

    /**
     * This abstract method is the place where should be initialized all view elements or methods
     * that will initialize them. This method could be called in [onCreateView] or [onViewCreated].
     */
    abstract fun initView()

    /**
     * This abstract method is the place where should be called the first logic to get data or the
     * main function of the view. This method could be called in [initView].
     */
    abstract fun initData()

    protected fun initBaseViewComponents() {
        with(requireActivity()) {
            bottomNavigationView = this.findViewById(R.id.dashboardBottomNavigationView)
            initCommonViews()
        }
    }

    private fun initCommonViews() = with(requireActivity()) {
        commonViewLoading = findViewById(R.id.loadingLayout)
        commonViewLoading.setOnClickListener { } // Do nothing while this is presented
    }

    fun displayLoadingView(display: Boolean) {
        if (isAdded) {
            requireActivity().runOnUiThread {
                with(commonViewLoading) {
                    if (display) show() else hide()
                }
            }
        }
    }

    fun displayToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if (isAdded) {
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), message, duration).show()
            }
        }
    }
}