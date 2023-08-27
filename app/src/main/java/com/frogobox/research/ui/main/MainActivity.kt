package com.frogobox.research.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.frogobox.research.common.base.BaseBindActivity
import com.frogobox.research.common.delegate.PreferenceDelegates
import com.frogobox.research.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseBindActivity<ActivityMainBinding>() {

    var showedToday = false

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
    }

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var preferenceDelegates: PreferenceDelegates

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            // Call View Model Here
            viewModel.getData()
            Log.d(TAG, "View Model : ${viewModel::class.java.simpleName}")
        }
        Log.d("SampleDelegates Output", getTagMainDelegate())
        // TODO : Add your code here

        dailyCheck()

    }

    override fun initView() {
        super.initView()
        binding.apply {

        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.apply {

        }
    }

    private fun dailyCheck() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val today = "$year$month$dayOfMonth"


        val sharedPref = getSharedPreferences("dailyCheck", MODE_PRIVATE)
        val currentDay = sharedPref.getBoolean(today, false)

        if (!currentDay) {
            Toast.makeText(this, "Daily Reward", Toast.LENGTH_SHORT).show()
            sharedPref.edit().putBoolean(today, true).apply()
        } else {
            Toast.makeText(this, "Already Claimed", Toast.LENGTH_SHORT).show()
        }

    }

}