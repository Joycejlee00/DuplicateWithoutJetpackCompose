package com.example.bottomnavwfab.ui.history

import android.os.Bundle
import android.view.*
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
    private var recipeList: List<Recipe> = listOf()

    //The onCreateView inflates the current fragment which is the History fragment.
    //It calls the fragment_history.xml file to display the said recyclerview
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    //onViewCreated initializes the recyclerView as well as calls the helper function that initializes
    //the recyclerView layout manager + adapter.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabHistory)
        initRecyclerView()

        fab.hide()

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
            if (viewModel.isEmpty()) fab.hide() else fab.show()

            if (it != null) {
                recipeList = it
                adapter.setRecipeList(it)
            } else {
                Toast.makeText(context, "Error in getting List", Toast.LENGTH_SHORT).show()
            }
        }

        // set the onclick listener for the FAB.
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
                //adapter.filter.filter(newText)
                if (newText != null) {
                    adapter.filter.filter(newText)
                }

                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
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