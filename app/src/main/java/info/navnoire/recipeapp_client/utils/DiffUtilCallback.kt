package info.navnoire.recipeapp_client.utils

import androidx.recyclerview.widget.DiffUtil
import info.navnoire.recipeapp_client.data.RecipeShortModel

class DiffUtilCallBack : DiffUtil.ItemCallback<RecipeShortModel>() {
    override fun areItemsTheSame(oldItem: RecipeShortModel, newItem: RecipeShortModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeShortModel, newItem: RecipeShortModel): Boolean {
        return oldItem.id == newItem.id
                && oldItem.title == newItem.title
                && oldItem.main_image_url == newItem.main_image_url
    }
}