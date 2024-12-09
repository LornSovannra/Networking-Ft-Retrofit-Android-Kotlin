package com.anonymous.networkingwithretrofit.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.anonymous.networkingwithretrofit.R
import com.anonymous.networkingwithretrofit.adapters.ProductAdapter
import com.anonymous.networkingwithretrofit.api.RetrofitInstance
import com.anonymous.networkingwithretrofit.databinding.ActivityMainBinding
import com.anonymous.networkingwithretrofit.responses.ProductList
import com.anonymous.networkingwithretrofit.services.ProductService
import com.anonymous.networkingwithretrofit.view_models.ProductViewModel
import retrofit2.Response

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpProductRV()
        fetchProducts()
        observeProducts()
        toggleProgressBar()

//        binding.btnRefresh.setOnClickListener {
//            refreshProducts()
//        }
    }

    private fun setUpProductRV(){
        productAdapter = ProductAdapter()
        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun fetchProducts(){
        productViewModel.fetchProducts()

//        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ProductService::class.java)
//
//        val responseProducts: LiveData<Response<ProductList>> = liveData {
//            val response = retrofitService.products()
//            emit(response)
//        }

//        responseProducts.observe(this, Observer {
//            val products = it.body()?.iterator()
//
//            if(products != null){
//                while (products.hasNext()){
//                    val product = products.next()
//
//                    Log.d(TAG, "PRODUCT: $product")
//                }
//            }
//        })
    }

    private fun observeProducts(){
        productViewModel.products.observe(this, Observer { products ->

//            if(products.isEmpty()){
//                binding.emptyContainer.visibility = View.VISIBLE
//            }else {
//                binding.emptyContainer.visibility = View.GONE
//                productAdapter.differ.submitList(products)
//            }

            productAdapter.differ.submitList(products)
        })
    }
//
//    private fun refreshProducts(){
//        binding.emptyContainer.visibility = View.GONE
//        productViewModel.fetchProducts()
//    }

    private fun toggleProgressBar(){
        productViewModel.isFetchingProduct.observe(this, Observer { isFetching ->

            if(isFetching){
                binding.progressBarContainer.visibility = View.VISIBLE
            } else {
                binding.progressBarContainer.visibility = View.GONE
            }
        })
    }
}