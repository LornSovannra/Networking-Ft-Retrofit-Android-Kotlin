package com.anonymous.networkingwithretrofit.services

import com.anonymous.networkingwithretrofit.responses.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun products(): Response<ProductList>
}