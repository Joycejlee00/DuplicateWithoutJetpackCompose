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
import android.widget.TextView
import android.widget.Toast
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
    lateinit var navController: NavController
    lateinit var fab: View
    lateinit var textView: TextView
    var labelList = ArrayList<String>()
    var unSortedList = ArrayList<Float>()
    var map = mutableMapOf<String, Float>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavBar

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_ingredient
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //floating action button
        fab = findViewById(R.id.fab)

        //checking if the permission has been granted by user
        fab.isEnabled = false
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 111)
        }
        else{
            //button is visible
            fab.isEnabled = true
        }


        fab.setOnClickListener { view ->
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 101)
        }
    }
}