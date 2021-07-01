package info.navnoire.recipeapp_client.ui.fragments.categorylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.databinding.FragmentCategoryListBinding
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.ui.adapters.CategoryListAdapter
import info.navnoire.recipeapp_client.utils.handleApiError
import info.navnoire.recipeapp_client.utils.visible

private const val TAG = "CategoryListFragment"

class CategoryListFragment : Fragment() {

    private val viewModel: CategoryListViewModel by activityViewModels()
    private val categoryListAdapter = CategoryListAdapter {
        viewModel.setParentCategory(it)
    }
    private lateinit var binding: FragmentCategoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryListAdapter
        }

        viewModel.categoryListData.observe(viewLifecycleOwner, {
            binding.categoryListProgressbar.visible(it is Result.Loading)
            when (it) {
                is Result.Success -> {
                    categoryListAdapter.submitList(it.data)
                    binding.categoryList.scheduleLayoutAnimation()
                }
                is Result.Error -> {
                    handleApiError(it) {
                        viewModel.currentCategoryId.value?.let { categoryId ->
                            viewModel.getCategoryList(
                                categoryId
                            )
                        }
                    }
                }
                is Result.Loading -> {
                }
            }
        })
    }
}