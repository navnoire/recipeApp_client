package info.navnoire.recipeapp_client.ui.fragments.categorylist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.data.repository.CategoryRepository
import info.navnoire.recipeapp_client.data.repository.UserPreferencesRepository
import info.navnoire.recipeapp_client.networking.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CategoryListViewModel"

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _categoryListData: MutableLiveData<Result<List<CategoryModel>>> = MutableLiveData()
    val categoryListData: LiveData <Result<List<CategoryModel>>>
        get() = _categoryListData

    private val  token : String = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYXZub2lyZSIsImlhdCI6MTYyNDY2NTYzNywiZXhwIjoxNjI0NjY5MjM3fQ.PzvF8DigbnLRsF8Zr6nWWqgESJat7F4RLqiht0RTbWuehDtd-jCXQ00RhGvO8CLA5i7WftUYY6nJ3iyn6w4KmQ"

    private val _currentCategory: MutableLiveData<CategoryModel> = MutableLiveData()
    val currentCategory: LiveData<CategoryModel>
        get() = _currentCategory

    fun getCategoryList(parentId: Int = 0) {
        viewModelScope.launch {
            _categoryListData.value = fetchChildCategory(parentId)
        }
    }

    fun setParentCategory(parent: CategoryModel) {
        _currentCategory.value = parent
    }


    private suspend fun fetchChildCategory(id: Int): Result<List<CategoryModel>> {
        Log.i(TAG, "fetchChildCategory: Bearer $token")
        return categoryRepository.fetchChildCategories(
            parentId = id,
            token = "Bearer $token"
        )
    }
}