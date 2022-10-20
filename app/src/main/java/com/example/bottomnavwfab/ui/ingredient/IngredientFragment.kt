package com.example.bottomnavwfab.ui.ingredient



import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.bottomnavwfab.ui.home.HomeFragment
import com.example.bottomnavwfab.R
import com.example.bottomnavwfab.databinding.FragmentIngredientBinding


class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextLayout : EditText = binding.editTextTextPersonName
        val cameraInput = this.arguments?.getString("label") //from camera
        Log.i(TAG, "cameraInput: " + cameraInput.toString())
        var currentText = ""
        val stringBuffer = StringBuffer()
//        if(editTextLayout.text.isEmpty()){
//            editTextLayout.setOnFocusChangeListener(object : View.OnFocusChangeListener {
//                override fun onFocusChange(v: View?, hasFocus: Boolean) {
//                    if (hasFocus) {
//                        //user has focused
//                        //editText.text.clear()
//                        currentText += editTextLayout.text.toString()
//                        Toast.makeText(context, "Typing", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
//        }
        stringBuffer.append(cameraInput.toString())
        editTextLayout.setText(stringBuffer.toString())

        //connect the generate button
        binding.generateButton.setOnClickListener { //grab the current text in the edittext field
            //Initialize the Ingredient Fragment class
            val setIngredients = editTextLayout.text.toString()
            Log.e("success", setIngredients)


            //pass setIngredients over to the home Fragment
            //val homeFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navigation_home) as HomeFragment
            val bundle = Bundle()
            bundle.putString("setIngredients", setIngredients)
            //pass the ingredient to the home fragment
            view.findNavController().navigate(R.id.navigation_home,bundle)
        }
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object{
        var TAG =  IngredientFragment::class.java.simpleName
    }
}
