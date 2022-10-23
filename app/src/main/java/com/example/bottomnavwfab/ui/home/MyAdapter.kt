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


class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClick: ((String, String, String) -> Unit)? = null

    //Set the recipeList to the parameter that takes in the RecipeData that is then called in the HomeFragment
    private var searchList: ArrayList<RecipeData> = ArrayList()
    private lateinit var recipeList: ArrayList<RecipeData>

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)

        return MyViewHolder(ItemRowBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        searchList.let { list ->
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
                    Glide.with(binding.recipeImage).load(listPosition.image)
                        .into(binding.recipeImage)

                    //have the expandable for the cardView when clicked
                    val isExpandable: Boolean = listPosition.expandable
                    binding.relativeLayout.visibility =
                        if (isExpandable) View.VISIBLE else View.GONE

                    binding.linearLayout.setOnClickListener {
                        listPosition.expandable = !listPosition.expandable
                        notifyDataSetChanged()

                    }

                    // when the save button is clicked, invoke the onclick listener located in the home fragment. this will save the recipe to the database
                    binding.fullRecipeButton.setOnClickListener {
                        onItemClick?.invoke(
                            listPosition.title.toString(),
                            listPosition.toString(),
                            listPosition.image.toString()
                        )
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    class MyViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}