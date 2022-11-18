package uz.infinity.mytask

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.infinity.mytask.databinding.ActivityMainBinding
import uz.infinity.mytask.presentation.vm.MainViewModel
import uz.infinity.mytask.presentation.vm.impl.MainViewModelImpl
import uz.infinity.mytask.utils.myLog

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var host: NavHostFragment
    private lateinit var graph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        host = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        graph = host.navController.navInflater.inflate(R.navigation.app_nav)

        viewModel.homeScreenLiveData.observe(this, homeScreenObserver)
        viewModel.loginScreenLiveData.observe(this, loginScreenObserver)
    }

    private val homeScreenObserver = Observer<Unit> {
        graph.setStartDestination(R.id.homeScreen)
        host.navController.graph = graph
    }
    private val loginScreenObserver = Observer<Unit> {
        graph.setStartDestination(R.id.loginScreen)
        host.navController.graph = graph
    }

    fun clearSteak(list: ArrayList<Int>, newScreenID: Int) {
        while (list.indexOf(host.navController.currentDestination?.id) != -1) {
            myLog("call")
            host.navController.popBackStack()
        }
        host.navController.navigate(newScreenID)
    }
}