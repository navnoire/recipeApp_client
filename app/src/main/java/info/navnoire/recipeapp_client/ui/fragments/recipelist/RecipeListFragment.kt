package info.navnoire.recipeapp_client.ui.fragments.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.databinding.FragmentRecipeListBinding
import info.navnoire.recipeapp_client.ui.adapters.RecipeListPagingAdapter
import info.navnoire.recipeapp_client.utils.visible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RecipeListFragment"

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private lateinit var binding: FragmentRecipeListBinding
    private val recipeListAdapter = RecipeListPagingAdapter()
    {
        val bundle = bundleOf(
            "recipeId" to it.id,
            "recipeTitle" to it.title,
            "imageUrl" to it.main_image_url
        )
        findNavController().navigate(
            R.id.action_recipeListFragment_to_recipeDetailsActivity,
            bundle
        )
    }

    private val args: RecipeListFragmentArgs by navArgs()
    private val recipeListViewModel : RecipeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchRecipes()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recipeList.apply {
            this.scheduleLayoutAnimation()
            this.layoutManager = GridLayoutManager(this.context, 2)
            this.adapter = recipeListAdapter
        }
    }

    private fun fetchRecipes() {
        lifecycleScope.launch {
            recipeListViewModel.fetchRecipeList(args.filterType, args.categoryId, args.searchString)
                .collectLatest { pagingData ->
                    recipeListAdapter.submitData(pagingData)
                }
        }
    }
}