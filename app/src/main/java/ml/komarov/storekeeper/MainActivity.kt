package ml.komarov.storekeeper

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import ml.komarov.storekeeper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.app_settings),
            Context.MODE_PRIVATE
        )

        setSupportActionBar(binding.appBarMain.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        // Save nav choice
        val navSavedChoice =
            sharedPreferences.getInt("navigationChoice", navView.menu.getItem(0).itemId)
        openNavigationItemSelected(navSavedChoice)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_print, R.id.nav_stock, R.id.nav_prices
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        sharedPreferences.edit().putInt("navigationChoice", item.itemId).apply()

        val handled = NavigationUI.onNavDestinationSelected(item, navController)
        if (handled) {
            val parent = binding.navView.parent
            if (parent is Openable) {
                parent.close()
            } else {
                val bottomSheetBehavior = NavigationUI.findBottomSheetBehavior(binding.navView)
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
        return handled
    }

    private fun openNavigationItemSelected(itemId: Int) {
        for (i in 0 until binding.navView.menu.size) {
            val item = binding.navView.menu.getItem(i)
            if (item.itemId == itemId) {
                onNavigationItemSelected(item)
            }
        }
    }
}