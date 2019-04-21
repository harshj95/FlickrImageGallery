package com.application.flickr.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.application.flickr.R
import com.application.flickr.data.api.model.Resource
import com.application.flickr.data.api.model.Status
import com.application.flickr.data.model.Image
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.ui.common.BaseActivity
import com.application.flickr.ui.main.adapter.ImageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        mainViewModel.getImagesForSearchTerm("delhi", 1).observe(this, Observer {
            onTodoResponse(it)
        })
    }

    private fun onTodoResponse(resource: Resource<List<UrlEntity>>?) {
        resource?.let {
            resource.data?.let {
                Log.d("qweqwe - status", "${resource.status}")
                when (resource.status) {
                    Status.SUCCESS -> handleSuccess(it)
                    Status.ERROR -> Toast.makeText(this, resource.errorMessage, Toast.LENGTH_SHORT).show()
                    Status.LOADING -> Toast.makeText(this, "load", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleSuccess(todoEntities: List<UrlEntity>) {
        ImageAdapter(todoEntities).also {
            Log.d("qweqwe - successsize", "${todoEntities.size}")
            it.setHasStableIds(true)
            recycler_view.adapter = it
            recycler_view.changeGrid(2)
        }
    }
}