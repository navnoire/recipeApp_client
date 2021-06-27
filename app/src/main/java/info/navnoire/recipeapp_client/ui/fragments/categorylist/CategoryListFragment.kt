package info.navnoire.recipeapp_client.ui.fragments.categorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import info.navnoire.recipeapp_client.databinding.FragmentCategoryListBinding
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.ui.adapters.CategoryListAdapter
import info.navnoire.recipeapp_client.utils.visible

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
        binding.categoryListProgressbar.visible(true)
        binding.categoryList.apply {
            layoutManager = LinearLayoutManager(context)
            viewModel.categoryListData.observe(viewLifecycleOwner, {
                binding.categoryListProgressbar.visible(it is Result.Loading)
                when (it) {
                    is Result.Success -> {
                        categoryListAdapter.submitList(it.data)
                        binding.categoryList.scheduleLayoutAnimation()
                    }
                    is Result.Error -> Toast.makeText(
                        context,
                        "Error: ${it.errorCode.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    is Result.Loading ->null
                }
            })
            adapter = categoryListAdapter
        }
    }
}