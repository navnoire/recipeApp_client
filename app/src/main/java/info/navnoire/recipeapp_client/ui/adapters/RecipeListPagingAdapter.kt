package info.navnoire.recipeapp_client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.data.RecipeShortModel
import info.navnoire.recipeapp_client.databinding.ItemRecipeCardBinding
import info.navnoire.recipeapp_client.utils.DiffUtilCallBack

class RecipeListPagingAdapter(private val itemClick: (RecipeShortModel) -> Unit) :
    PagingDataAdapter<RecipeShortModel, RecipeListPagingAdapter.RecipeListViewHolder>(
        DiffUtilCallBack()
    ) {

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindRecipe(it, itemClick) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val binding = ItemRecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    class RecipeListViewHolder(private val binding: ItemRecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindRecipe(recipe: RecipeShortModel, itemClick: (RecipeShortModel) -> Unit) {
            binding.run {
                singleRecipeTitle.text = recipe.title
                Picasso.get()
                    .load(recipe.main_image_url)
                    .into(recipeIcon)
                itemView.setOnClickListener { itemClick(recipe) }
            }

        }
    }
}
