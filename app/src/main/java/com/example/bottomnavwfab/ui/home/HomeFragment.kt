package com.example.bottomnavwfab.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavwfab.R
import com.example.bottomnavwfab.databinding.FragmentHomeBinding
import com.example.bottomnavwfab.data.RecipeData
import com.example.bottomnavwfab.database.Recipe
import com.example.bottomnavwfab.ui.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Declare the viewModel, RecyclerView, and Adapter
    private val viewModel: HomeViewModel by viewModels()
    private val dbViewModel: HistoryViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapter = MyAdapter()
    private var recipeList = ArrayList<RecipeData>()


    //The onCreateView inflates the current fragment which is the Home fragment.
    //It calls the fragment_home.xml file to display the said recyclerview
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    /**
     * onViewCreated initializes the recyclerView as well as calls the helper function that initializes
     * the recyclerView layout manager + adapter.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize the string that is being passed from the Ingredient Fragment
        val sendString = this.arguments?.getString("setIngredients")

        recyclerView = view.findViewById(R.id.recyclerView)
        initRecyclerView()

        /**
         * Repository is initialized as well as the viewModelFactory that calls the HomeViewModel class
         * The HomeViewModel class is where all the data is being grabbed (e.i: The title, recipe ingredients, etc)
         */
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                recipeList = it
                adapter.setRecipeList(it)
            } else {
                Toast.makeText(context, "Error in getting List", Toast.LENGTH_SHORT).show()
            }
        }
        //callGenerateList takes in a string parameter of the ingredient and displays the recyclerview
        viewModel.callGenerateList(sendString.toString())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //second filter function for future implementation
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "Type to search for recipes!"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filter(newText)
                }
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    //first filter function used for testing purposes
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<RecipeData> = ArrayList()

        // running a for loop to compare elements.
        for (item in recipeList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title?.lowercase(Locale.getDefault())
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isNotEmpty()) {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }

    /**
     * The helperFunction that initializes the RecyclerView
     */
    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        // this will run when the save button is clicked in the recipe fragment
        adapter.onItemClick = { title, ingredients, image ->
            Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show()
            val recipeObject = Recipe(title, ingredients, image)
            dbViewModel.addRecipe(recipeObject)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}