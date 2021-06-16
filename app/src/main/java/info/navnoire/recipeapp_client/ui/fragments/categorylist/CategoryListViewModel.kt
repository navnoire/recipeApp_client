package info.navnoire.recipeapp_client.ui.fragments.categorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.data.Result
import info.navnoire.recipeapp_client.data.source.remote.CategoryRepository
import kotlinx.coroutines.launch

class CategoryListViewModel : ViewModel() {
    private val categoryRepository = CategoryRepository()
    private val categoryListData: MutableLiveData<List<CategoryModel>> = MutableLiveData()

    fun getCategoryList(parentId: Int = 0): LiveData<List<CategoryModel>> {
        viewModelScope.launch {
            categoryListData.value = fetchChildCategory(parentId)
        }
        return categoryListData
    }

    private suspend fun fetchChildCategory(id: Int): List<CategoryModel> {
        return when (val result = categoryRepository.fetchChildCategories(parentId = id)) {
            is Result.Success -> result.data
            else -> emptyList()
        }
    }
}