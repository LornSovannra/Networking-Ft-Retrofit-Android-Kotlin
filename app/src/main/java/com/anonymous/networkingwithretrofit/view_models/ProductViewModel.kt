package com.anonymous.networkingwithretrofit.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.networkingwithretrofit.api.RetrofitInstance
import com.anonymous.networkingwithretrofit.responses.ProductList
import com.anonymous.networkingwithretrofit.services.ProductService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "ProductViewModel"

class ProductViewModel: ViewModel() {
    private val retrofitService: ProductService = RetrofitInstance.getRetrofitInstance().create(ProductService::class.java)
    private val _products = MutableLiveData<ProductList>()
    private val _isFetchingProduct = MutableLiveData<Boolean>(false)

    val products: LiveData<ProductList> = _products
    val isFetchingProduct: LiveData<Boolean> = _isFetchingProduct

    fun fetchProducts(){
        viewModelScope.launch {
            _isFetchingProduct.value = true

            delay(2000)
            val response = retrofitService.products()
            _products.value = response.body()

            _isFetchingProduct.value = false
        }
    }
}