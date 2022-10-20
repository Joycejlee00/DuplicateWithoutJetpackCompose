package com.example.bottomnavwfab.ui.home

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavwfab.R
import com.example.bottomnavwfab.data.RecipeData
import com.example.bottomnavwfab.databinding.ItemRowBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*
import kotlin.collections.ArrayList

/**
 * The adapter class the connects the RecyclerView with the data that is being grabbed
 */


class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {


    var onItemClick: ((String, String, String) -> Unit)? = null

    //Set the recipeList to the parameter that takes in the RecipeData that is then called in the HomeFragment

    private lateinit var context: Context
    var searchList: ArrayList<RecipeData> = ArrayList()
    lateinit var  recipeList: ArrayList<RecipeData>




    //Set the recipeList to the parameter that takes in the RecipeData that is then called in the HomeFragment
    @JvmName("setRecipeList1")
    fun setRecipeList(myReceipeList: ArrayList<RecipeData>) {
        this.recipeList = myReceipeList
        searchList = recipeList

        //update the adapter with the new set of data
        notifyDataSetChanged()
    }

    // used when TESTING to see where to add our filtered list
    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<RecipeData>) {
        // below line is to add our filtered
        // list in our course array list.
        // recipeList = filterlist
        searchList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    //CURRENT FILTER for SEARCHVIEW
    //filter function based on what user inputs on SearchView
    override fun getFilter(): Filter {
        return object: Filter(){

            //checks if the user typed a text in the SearchView
            //if there is no text, return all the items
            override fun performFiltering(searchChar: CharSequence?): FilterResults {
                val charSearch = searchChar.toString()
                if (charSearch.isEmpty()) {
                    searchList = recipeList
                } else {
                    val resultList = ArrayList<RecipeData>()
                    for (recipe in recipeList!!) {
                        if (recipe.title?.lowercase(Locale.ROOT)
                                ?.contains(charSearch.lowercase(Locale.ROOT)) == true
                        ) {
                            resultList.add(recipe)
                        }
                    }
                    // recipeList = resultList
                    searchList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults

            }

            //gets the results and pass them to searchList, then updates RecyclerView
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                searchList = p1?.values as ArrayList<RecipeData> /* = java.util.ArrayList<com.example.bottomnav.data.RecipeData> */
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)

        return MyViewHolder(ItemRowBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /**
         * This ensures that recipelist is not null
         */
        searchList?.let { list ->
            //use view binding to get rid of using 'findviewbyid'
            with(holder) {
                with(list[position]) {

                    //use ctrl+q to set listPosition as a recipeData object
                    val listPosition = list[position]

                    //set the title + ingredients to appropriate data
                    binding.tvTitle.text = listPosition.title
                    binding.tvRecipeIngredients.text = listPosition.toString()

                    Log.e("title", binding.tvTitle.text.toString())
                    /**
                     * Glide: an opensource image/media framework for android to
                     * load in images/media onto an imageView
                     */
                    Glide.with(binding.recipeImage).load(listPosition.image).into(binding.recipeImage)
                    //  Log.e("recipeHere: ", list[position].title.toString())

                    //have the expandable for the cardView when clicked
                    val isExpandable: Boolean = listPosition.expandable
                    binding.relativeLayout.visibility =
                        if (isExpandable) View.VISIBLE else View.GONE

                    binding.linearLayout.setOnClickListener {
                        val recipes = listPosition
                        recipes.expandable = !recipes.expandable
                        //notifyItemChanged(position)

                        //this line fixes the problem
                        //where the cardview won't expand with properly with the content
                        notifyDataSetChanged()

                    }

                    // when the save button is clicked, invoke the onclick listener located in the home fragment. this will save the recipe to the database
                    binding.fullRecipeButton.setOnClickListener {
                        onItemClick?.invoke(listPosition.title.toString(), listPosition.toString(), listPosition.image.toString())
                    }
                }
            }
            //The question mark -> if the recipe list is not null the next block of code will be executed.
            //it is a preserved word\
        } ?: {
            //if recipe IS null -> do whatever is within this curly braces
        }
    }



    override fun getItemCount(): Int {
//        if (recipeList == null) return 0
//        else return recipeList?.size!!

        return searchList.size

    }

    class MyViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}
