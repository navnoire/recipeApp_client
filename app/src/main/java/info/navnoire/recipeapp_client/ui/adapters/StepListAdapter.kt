package info.navnoire.recipeapp_client.ui.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.data.StepModel
import info.navnoire.recipeapp_client.databinding.ItemStepBinding

class StepListAdapter(
    private val values: List<StepModel>
) : RecyclerView.Adapter<StepListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values[position].let { holder.bindStep(it) }
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(private val binding : ItemStepBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindStep(model : StepModel) {
            binding.run {
                this.stepText.text = Html.fromHtml(model.text.replace("\n","<br>"))
                Picasso.get().load(model.stepImageUrl).into(this.stepImage)
            }
        }
    }

}