package info.navnoire.recipeapp_client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.databinding.ItemCategoryBinding

class CategoryListAdapter(
    private val itemClick: (CategoryModel) -> Unit
) : ListAdapter<CategoryModel, CategoryListViewHolder>(CategoryDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        getItem(position).let { holder.bindCategory(it, itemClick) }
    }
}

class CategoryListViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindCategory(category: CategoryModel, itemClick: (CategoryModel) -> Unit) {
        binding.run {
            categoryTitle.text = category.title
            categoryTitle.setOnClickListener { itemClick(category) }
        }
    }
}

object CategoryDiff : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
        oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.hasChild == newItem.hasChild
}
