package com.example.internet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internet.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            } catch (e: IOException) {
                Log.e("Log*", "IOexeption, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("Log_", "HttpEXception , unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                todoAdapter.todos = response.body()!!
            } else {
                Log.e("log**", "response not successful")
            }
            binding.progressBar.isVisible = false

        }
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}