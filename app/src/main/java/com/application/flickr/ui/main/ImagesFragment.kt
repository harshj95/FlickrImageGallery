package com.application.flickr.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.application.flickr.R
import com.application.flickr.data.api.model.Resource
import com.application.flickr.data.api.model.Status
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.ui.common.BaseFragment
import com.application.flickr.ui.main.adapter.GridPickerDialog
import com.application.flickr.ui.main.adapter.ImageAdapter
import com.application.flickr.util.extensions.hideKeyboard
import kotlinx.android.synthetic.main.fragment_photos.*

/**
 * Created by Harsh Jain on 21/04/19.
 */

class ImagesFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }
    private val imageAdapter = ImageAdapter().also { it.setHasStableIds(true) }
    private var searchTerm = "dogs" // default search keyword
    private val initialPage = 1
    private var currentPage = initialPage
    private val incrementCoefficient = 1
    private var currentColumns = 2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("SEARCH_TERM")?.let {
            searchTerm = it
            etSearch.setText(it)
        }

        etSearch.clearFocus()
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchTerm = etSearch.text.toString()
                imageAdapter.clear()
                currentPage = 0
                fetchPhotos(initialPage)
                etSearch.hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })

        mainViewModel.columns.observe(this, Observer {
            this.currentColumns = it
            imageRecyclerView.changeGrid(it)
        })

        columnPickerIcon.setOnClickListener {
            GridPickerDialog.newInstance(currentColumns).show(activity!!.supportFragmentManager, "COL_PICKER")
        }

        imageRecyclerView.apply {
            adapter = imageAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        currentPage += incrementCoefficient
                        fetchPhotos(currentPage)
                    }
                }
            })
        }

        fetchPhotos(initialPage) // initial load with default search term
    }

    private fun fetchPhotos(page: Int) {
        mainViewModel.getImagesForSearchTerm(searchTerm, page).observe(this, Observer {
            onImagesResponse(it)
        })
    }

    private fun onImagesResponse(resource: Resource<List<UrlEntity>>?) {
        resource?.let {
            resource.data?.let {
                when (resource.status) {
                    Status.SUCCESS -> handleSuccess(it)
                    Status.ERROR -> Toast.makeText(context, resource.errorMessage, Toast.LENGTH_SHORT).show()
                    Status.LOADING -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleSuccess(images: List<UrlEntity>) {
        imageAdapter.append(images)
    }

    companion object {
        internal fun newInstance(searchTerm: String?): ImagesFragment {
            val bundle = Bundle()
            bundle.putString("SEARCH_TERM", searchTerm)
            val imagesFragment = ImagesFragment()
            imagesFragment.arguments = bundle
            return imagesFragment
        }
    }
}