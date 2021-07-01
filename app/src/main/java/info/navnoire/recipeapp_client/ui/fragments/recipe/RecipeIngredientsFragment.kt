package info.navnoire.recipeapp_client.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.databinding.FragmentRecipeIngredientsBinding
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.ui.adapters.IngredientListAdapter
import info.navnoire.recipeapp_client.utils.handleApiError

class RecipeIngredientsFragment : Fragment() {
    private val viewModel : RecipeDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentRecipeIngredientsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.singleRecipeData.observe(viewLifecycleOwner, {
            when(it) {
                is Result.Success -> {
                    val adapter = IngredientListAdapter(it.data.ingredients)
                    binding.ingredientList.adapter = adapter
                }
                is Result.Error -> {
                    handleApiError(it){viewModel.loadRecipe(viewModel.currentRecipeId.value?:2)}
                }
                is Result.Loading -> {}
            }
        })

    }
}