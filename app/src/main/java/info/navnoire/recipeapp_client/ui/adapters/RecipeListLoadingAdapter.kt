package info.navnoire.recipeapp_client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import info.navnoire.recipeapp_client.databinding.ItemRecipeListLoadingStateBinding
import info.navnoire.recipeapp_client.utils.visible

class RecipeListLoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RecipeListLoadingAdapter.LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemRecipeListLoadingStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadingStateViewHolder(binding = binding, retry = retry)
    }

    inner class LoadingStateViewHolder(
        val binding: ItemRecipeListLoadingStateBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindState(loadState: LoadState) {
            binding.run {
                progressbar.visible(loadState is LoadState.Loading)
                binding.errorText.visible(loadState is LoadState.Error)
                binding.retryButton.visible(loadState is LoadState.Error)
            }
        }
    }
}