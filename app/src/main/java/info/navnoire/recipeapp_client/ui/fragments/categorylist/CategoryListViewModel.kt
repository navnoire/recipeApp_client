package info.navnoire.recipeapp_client.ui.fragments.categorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.data.repository.CategoryRepository
import info.navnoire.recipeapp_client.networking.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CategoryListViewModel"

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _categoryListData: MutableLiveData<Result<List<CategoryModel>>> = MutableLiveData()
    val categoryListData: LiveData<Result<List<CategoryModel>>>
        get() = _categoryListData

    private val _currentCategory: MutableLiveData<CategoryModel> = MutableLiveData()
    val currentCategory: LiveData<CategoryModel>
        get() = _currentCategory

    private val _currentCategoryId :MutableLiveData<Int> = MutableLiveData()
    val currentCategoryId: LiveData<Int>
    get() = _currentCategoryId

    fun getCategoryList(parentId: Int = 0) {
        _currentCategoryId.value = parentId
        viewModelScope.launch {
            _categoryListData.value = Result.Loading
            _categoryListData.value = categoryRepository.fetchChildCategories(parentId = parentId)
        }
    }

    fun setParentCategory(parent: CategoryModel) {
        _currentCategory.value = parent
    }
}