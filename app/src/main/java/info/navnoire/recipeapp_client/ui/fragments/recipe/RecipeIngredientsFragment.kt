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
import info.navnoire.recipeapp_client.ui.adapters.IngredientListAdapter

class RecipeIngredientsFragment : Fragment() {
    private val viewModel : RecipeDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentRecipeIngredientsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("INGREDIENTS FRAGMENT","My parent activity is $activity")
        binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.singleRecipeData.observe(viewLifecycleOwner, {
            val adapter = IngredientListAdapter(it.ingredients)
            binding.ingredientList.adapter = adapter
        })

    }
}