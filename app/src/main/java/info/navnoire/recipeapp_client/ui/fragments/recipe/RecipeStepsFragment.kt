package info.navnoire.recipeapp_client.ui.fragments.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.databinding.ActivityRecipeDetailsBinding
import info.navnoire.recipeapp_client.databinding.FragmentRecipeIngredientsBinding
import info.navnoire.recipeapp_client.databinding.FragmentRecipeStepsBinding
import info.navnoire.recipeapp_client.ui.adapters.StepListAdapter

class RecipeStepsFragment : Fragment() {
    private val viewModel by activityViewModels<RecipeDetailsViewModel>()
    private lateinit var binding : FragmentRecipeStepsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeStepsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.stepsList.layoutManager = LinearLayoutManager(context)
        viewModel.singleRecipeData.observe(viewLifecycleOwner, {
            val adapter = StepListAdapter(it.steps)
            binding.stepsList.adapter = adapter
        })

    }


}