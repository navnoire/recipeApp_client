package info.navnoire.recipeapp_client.ui.fragments.categorylist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.databinding.FragmentCategoryListBinding
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType
import info.navnoire.recipeapp_client.ui.adapters.CategoryListAdapter

class CategoryListFragment : Fragment() {
    private val args : CategoryListFragmentArgs by navArgs()
    private val viewModel: CategoryListViewModel by lazy {
        ViewModelProvider(this).get(CategoryListViewModel::class.java)
    }
    private val categoryListAdapter = CategoryListAdapter {
        when(it.hasChild) {
            true -> {
                val bundle = bundleOf("categoryId" to it.id)
                findNavController().navigate(R.id.action_categoryListFragment_self, bundle)
            }
            false -> {
                val bundle = bundleOf("categoryId" to it.id, "filterType" to RecipeListFilterType.BY_CATEGORY)
                findNavController().navigate(R.id.action_categoryListFragment_to_recipeListFragment, bundle)
            }
        }
    }
    private lateinit var binding: FragmentCategoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.categoryList.apply {
                layoutManager = LinearLayoutManager(context)
                viewModel.getCategoryList(args.categoryId).observe(viewLifecycleOwner, {
                        list -> categoryListAdapter.submitList(list)
                })
                adapter = categoryListAdapter
            }
    }
}