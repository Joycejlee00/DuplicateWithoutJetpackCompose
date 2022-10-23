package com.example.bottomnavwfab.ui.history
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavwfab.databinding.ItemRowHistoryBinding
import com.example.bottomnavwfab.R
import com.example.bottomnavwfab.database.Recipe
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>(), Filterable {

    private lateinit var allRecipes: List<Recipe>
    var historyList: List<Recipe> = listOf()

    var onItemClick: ((Recipe) -> Unit)? = null

    fun setRecipeList(recipeList: List<Recipe>) {
        this.allRecipes = recipeList
        historyList = allRecipes

        //notify the adapter with new set of data
        notifyDataSetChanged()
    }

    //filter function based on what user inputs on SearchView
    override fun getFilter(): Filter {
        return object: Filter(){

            //checks if the user typed a text in the SearchView
            //if there is no text, return all the items
            override fun performFiltering(searchChar: CharSequence?): FilterResults {
                val charSearch = searchChar.toString()
                historyList = if (charSearch.isEmpty()) {
                    allRecipes
                } else {
                    val resultList: MutableList<Recipe> = mutableListOf()
                    for (recipe in allRecipes) {
                        if (recipe.title.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(recipe)
                        }
                    }
                    // recipeList = resultList
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = historyList
                return filterResults

            }

            //gets the results and pass them to searchList, then updates RecyclerView
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                historyList = p1?.values as java.util.ArrayList<Recipe> /* = java.util.ArrayList<com.example.bottomnav.data.RecipeData> */
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_history,
            parent, false
        )

        return MyViewHolder(ItemRowHistoryBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        historyList.let { list ->
            with(holder) {
                with(list[position]) {
                    // on below line we are setting data to item of recycler view.
                    binding.tvHistoryTitle.text = historyList[position].title
                    binding.tvRecipeIngredientsHistory.text = historyList[position].ingredients

                    // add this when I am storing and retrieving the images
                    Glide.with(binding.recipeImage).load(list[position].image).into(binding.recipeImage)

                    val isExpandable: Boolean = list[position].expandable
                    binding.relativeLayout.visibility =
                        if (isExpandable) View.VISIBLE else View.GONE

                    binding.linearLayout.setOnClickListener {
                        val recipes = list[position]
                        recipes.expandable = !recipes.expandable
                        //notifyItemChanged(position)

                        //this line was newly added
                        notifyDataSetChanged()
                    }

                    // this will prompt the user to delete a single item.
                    binding.btnDeleteSingle.setOnClickListener {
                        onItemClick?.invoke(list[position])
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class MyViewHolder(val binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}