package com.plub.ccptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.plub.ccptest.data.remote.JokeResponse
import com.plub.ccptest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitService
    private lateinit var binding: ActivityMainBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = RetrofitClient.getInstance()
        retrofitService = retrofit.create(RetrofitService::class.java)

        getJokeCategories()

        binding.ib.setOnClickListener {
            getRandomJoke()
        }
    }

    private fun getJokeCategories() {
        retrofitService.getJokeCategories().enqueue(object  : Callback<MutableList<String>> {
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {
                val categories = response.body()!!
                categories.add(0, "random")
                spinnerAdapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_spinner_item, categories)
                binding.spinner.adapter = spinnerAdapter
                binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                        category = if(spinnerAdapter.getItem(pos) == "random") null else spinnerAdapter.getItem(pos)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) { }
                }

                binding.flLoading.visibility = View.GONE
            }

            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {

            }
        })


    }

    private fun getRandomJoke() {
        retrofitService.getRandomJoke(category).enqueue(object : Callback<JokeResponse> {
            override fun onResponse(call: Call<JokeResponse>, response: Response<JokeResponse>) {
                if(response.isSuccessful) binding.tv.text = response.body()!!.value
            }

            override fun onFailure(call: Call<JokeResponse>, t: Throwable) {

            }
        })
    }
}