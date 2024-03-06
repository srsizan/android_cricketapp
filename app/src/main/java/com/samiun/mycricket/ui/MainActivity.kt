package com.samiun.mycricket.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.samiun.mycricket.R
import com.samiun.mycricket.databinding.ActivityMainBinding
import com.samiun.mycricket.utils.ConectivityChecker
import com.samiun.mycricket.utils.GetCricketWorker
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), ConectivityChecker.ConnectivityReceiverListener {
    private lateinit var work: PeriodicWorkRequest
    private lateinit var navController: NavController
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var isPreviouslyConnected = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)


        registerReceiver(ConectivityChecker(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        work = PeriodicWorkRequestBuilder<GetCricketWorker>(3, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "fetchData",

            /*
            THis need to be changed

            */
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConectivityChecker.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (isConnected && !isPreviouslyConnected) {
            Snackbar.make(binding.navHostFragmentContainer, "Connection Restored!", Snackbar.LENGTH_SHORT).show()
        }else if (!isConnected) {
            Snackbar.make(binding.navHostFragmentContainer, "Not Connected to Internet", Snackbar.LENGTH_SHORT).show()
        }
        isPreviouslyConnected = isConnected
    }

}