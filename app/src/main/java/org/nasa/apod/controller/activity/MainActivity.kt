package org.nasa.apod.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import org.nasa.apod.R
import org.nasa.apod.databinding.ActivityMainBinding
import org.nasa.apod.db.database.APODDataBase
import org.nasa.apod.util.Network
import org.nasa.apod.util.NetworkInterface
import org.nasa.apod.viewmodel.APODViewModel
import org.nasa.apod.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), NetworkInterface {

    private lateinit var networkInterface: Network
    private lateinit var viewModel: APODViewModel
    private lateinit var binding: ActivityMainBinding
    private var isNetworkConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkInterface = Network(this.applicationContext, this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(APODDataBase.getInstance(this.application))
            )[APODViewModel::class.java]
        registerObserver()
        networkInterface.registerNetworkCallback()
    }

    private fun registerObserver() {
        viewModel.dailyData.observe(this, {
            if (it != null) {
                binding.imgView.visibility = View.VISIBLE
                binding.dailyData = it
            }
        })
        viewModel.lastSeenData.observe(this, {
            if (!isNetworkConnected && it != null) {
                binding.imgView.visibility = View.VISIBLE
                binding.dailyData = it
                showErrorMsg(getString(R.string.error_msg))
            } else {
                binding.imgView.visibility = View.GONE
                showErrorMsg(getString(R.string.error_msg_with_no_data))
            }
        })
    }

    private fun callApi() {
        viewModel.getDailyAPOD()
    }

    private fun showErrorMsg(msg: String) {
        Snackbar.make(binding.dailyLayout, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        networkInterface.unregisterNetworkCallback()
    }

    override fun isNetworkAvailable(isNetwork: Boolean) {
        isNetworkConnected = isNetwork
        callApi()
    }
}
