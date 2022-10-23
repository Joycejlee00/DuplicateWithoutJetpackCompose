package com.example.bottomnavwfab



import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bottomnavwfab.databinding.ActivityMainBinding
import com.example.bottomnavwfab.ui.ingredient.IngredientFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.image.TensorImage
import java.lang.Boolean
import kotlin.Array
import kotlin.Float
import kotlin.Int
import kotlin.IntArray
import kotlin.Pair
import kotlin.String
import kotlin.arrayOf


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var fab: View

    //creating sharedViewModel in order for us to send data over

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.bottomNavBar

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val config = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, config)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_add_24)
                    binding.toolbar.setNavigationOnClickListener {
                        controller.navigate(R.id.navigation_ingredient)
                    }
                    binding.toolbar.title = " "
                }
                R.id.navigation_history -> {
                    binding.toolbar.navigationIcon = null
                    binding.toolbar.title = " "
                }
                else -> {
                    binding.toolbar.navigationIcon = null
                }
            }
        }

        //floating action button
        fab = findViewById(R.id.fab)

        //checking if the permission has been granted by user
        fab.isEnabled = false
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                111
            )
        } else {
            //button is visible
            fab.isEnabled = true
        }

        fab.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 101)
        }
    }
}
