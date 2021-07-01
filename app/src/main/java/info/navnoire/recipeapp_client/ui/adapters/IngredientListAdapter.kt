package info.navnoire.recipeapp_client.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import info.navnoire.recipeapp_client.R
import info.navnoire.recipeapp_client.data.IngredientModel
import info.navnoire.recipeapp_client.data.IngredientType

class IngredientListAdapter(private val list : List<IngredientModel>) :
    BaseAdapter() {

    override fun getViewTypeCount(): Int = IngredientType.values().size
    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal
    override fun getCount(): Int = list.size
    override fun getItem(position: Int): IngredientModel = list[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ingredient = getItem(position)
        val itemType = getItemViewType(position)
        val view = convertView ?: getInflatedLayoutForType(itemType, parent)

        when (ingredient.type) {
            IngredientType.ORDINARY -> {
                val nameView: TextView = view.findViewById(R.id.ingredient_ordinary_name)
                val amountView: TextView = view.findViewById(R.id.ingredient_ordinary_amount)
                with(ingredient) {
                    nameView.text = this.name
                    amountView.text = this.amount
                }
            }
            IngredientType.HEADER -> {
                val headerView: TextView = view.findViewById(R.id.ingredient_header_text)
                with(ingredient) {
                    headerView.text = this.name
                }
            }
        }
        return view
    }

    private fun getInflatedLayoutForType(itemType: Int, parent: ViewGroup): View {
        return when (itemType) {
            IngredientType.ORDINARY.ordinal -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient_regular, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient_header, parent, false)
        }
    }
}