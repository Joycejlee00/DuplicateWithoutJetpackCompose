package com.example.bottomnavwfab.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavwfab.R
import com.example.bottomnavwfab.database.Recipe
import com.example.bottomnavwfab.databinding.FragmentHistoryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    //Declare the viewModel, RecyclerView, and Adapter
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter
    var recipeList: List<Recipe> = listOf()

    //The onCreateView inflates the current fragment which is the History fragment.
    //It calls the fragment_history.xml file to display the said recyclerview
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_history, container, false)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    /**
     * onViewCreated initializes the recyclerView as well as calls the helper function that initializes
     * the recyclerView layout manager + adapter.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabHistory)
        initRecyclerView()

        // initially hide the fab
        fab.hide()

        /**
         * Repository is initialized as well as the viewModelFactory that calls the HistoryViewModel class
         * The HistoryViewModel class is where all the data is being grabbed (e.i: The title, recipe ingredients, etc)
         */

        // reformatted to account for the search widget
        adapter = HistoryAdapter()
        recyclerView.adapter = adapter

        adapter.onItemClick = { recipe ->
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Are you sure you want to delete?")
            builder?.setNegativeButton("Delete") { _, _ ->
                viewModel.deleteRecipe(recipe.title)
            }

            builder?.setPositiveButton("No") { _, _ ->
            }

            val alertDialog = builder?.create()
            alertDialog?.show()
        }

        // use an observer to set the recipe list in the adapter
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) {
            // if recipelist is populated, show the fab
            if (it.isNotEmpty()) {
                fab.show()
            }
            if (it != null) {

                recipeList = it
                adapter.setRecipeList(it)
                setupSearchView()
                //adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error in getting List", Toast.LENGTH_SHORT).show()
            }
        }

        // set the onclick listener for the FAB
        fab.setOnClickListener {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Are you sure you want to clear history?")
            builder?.setNegativeButton("Delete all") { _, _ ->
                viewModel.deleteAll()
                fab.hide()
            }

            builder?.setPositiveButton("No") { _, _ ->
            }

            val alertDialog = builder?.create()
            alertDialog?.show()
        }
    }


    private fun setupSearchView(){
        binding.historySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //when the user is done entering text and presses enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            //while entering text in the searchView
            override fun onQueryTextChange(msg: String?): Boolean {
                //val charSearch = newText
                adapter.filter.filter(msg)
                /*  if (msg != null) {
                      filter(msg)
                  }*/
                return false
            }

        })
    }



    // init the recycler view
    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}