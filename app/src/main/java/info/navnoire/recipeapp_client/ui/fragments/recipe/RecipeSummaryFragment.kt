package info.navnoire.recipeapp_client.ui.fragments.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.RecipeDetailsFlowNavigationArgs
import info.navnoire.recipeapp_client.databinding.FragmentRecipeStepsBinding
import info.navnoire.recipeapp_client.databinding.FragmentRecipeSummaryBinding

class RecipeSummaryFragment : Fragment() {
    private val args : RecipeDetailsFlowNavigationArgs by navArgs()
    private lateinit var binding: FragmentRecipeSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Picasso.get().setIndicatorsEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            singleRecipeTitle.text = args.recipeTitle
            Picasso.get().load(args.imageUrl).into(singleRecipeMainImage)
            ingrButton.setOnClickListener {
                findNavController().navigate(R.id.action_recipeSummaryFragment_to_recipeIngredientsFragment)
            }
            stepsButton.setOnClickListener {
                findNavController().navigate(R.id.action_recipeSummaryFragment_to_recipeStepsFragment)
            }
        }




    }

}