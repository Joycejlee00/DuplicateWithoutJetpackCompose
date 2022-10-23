package com.example.bottomnavwfab.ui.ingredient



import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.bottomnavwfab.ui.home.HomeFragment
import com.example.bottomnavwfab.R
import com.example.bottomnavwfab.databinding.FragmentIngredientBinding

class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private lateinit var editTextLayout: EditText
    private var userinputList = ArrayList<String>()
    private val binding get() = _binding!!
    private var ingredientList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: ")
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        editTextLayout = binding.editTextTextPersonName


        var textHolder: String? = ""

        editTextLayout.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                textHolder += editTextLayout.text.toString()
            if (textHolder != null) {
                userinputList.add(textHolder)
            }
            userinputList.joinToString(",")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")

        _binding = null
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
        val string = editTextLayout.text.toString()
        val outState = Bundle()
        outState.putString("savedInfo", string)
        this.onSaveInstanceState(outState)

        Log.i(TAG, "ingredientList: $ingredientList")
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")

        binding.generateButton.setOnClickListener {
            generateRecipes(it)
        }
    }

    companion object {
        var TAG: String = IngredientFragment::class.java.simpleName
    }

    private fun generateRecipes(view: View) {
        ingredientList.clear()

        val setIngredients = editTextLayout.text.toString()

        val bundle = Bundle()
        bundle.putString("setIngredients", setIngredients)
        //pass the ingredient to the home fragment
        view.findNavController().navigate(R.id.navigation_home, bundle)
        ingredientList.clear()
        editTextLayout.text.clear()
    }
}