package org.nasa.apod.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import org.nasa.apod.R
import org.nasa.apod.databinding.ActivityMainBinding
import org.nasa.apod.db.database.APODDataBase
import org.nasa.apod.model.DailyAPOD
import org.nasa.apod.util.Network
import org.nasa.apod.util.NetworkInterface
import org.nasa.apod.viewmodel.APODViewModel
import org.nasa.apod.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), NetworkInterface {

    private var isNetworkConnected: Boolean = false
    private lateinit var networkInterface: Network
    private lateinit var viewModel: APODViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        networkInterface = Network(this.applicationContext, this)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(APODDataBase.getInstance(this.application))
            )[APODViewModel::class.java]
        networkInterface.registerNetworkCallback()
        registerObserver()
    }

    private fun registerObserver() {
        viewModel.dailyData.observe(this, {
            if (it != null) {
                setBindingProperty(it)
            }
        })
        viewModel.lastSeenData.observe(this, {
            if (it != null) {
                setBindingProperty(it)
                if (!isNetworkConnected) {
                    showErrorMsg(getString(R.string.error_msg))
                }
            } else {
                binding.imgView.visibility = View.GONE
                showErrorMsg(getString(R.string.error_msg_with_no_data))
            }
        })
    }

    private fun setBindingProperty(dailyAPOD: DailyAPOD) {
        binding.imgView.visibility = View.VISIBLE
        binding.dailyData = dailyAPOD
        binding.executePendingBindings()
    }

    private fun callApi() {
        lifecycleScope.launchWhenResumed {
            viewModel.getDailyAPOD(isNetworkConnected)
        }
    }

    private fun showErrorMsg(msg: String) {
        Snackbar.make(binding.dailyLayout, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun isNetworkAvailable(isNetwork: Boolean) {
        isNetworkConnected = isNetwork
        callApi()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkInterface.unregisterNetworkCallback()
    }
}
