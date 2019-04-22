package com.application.flickr.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.application.flickr.App.Companion.kodeinInstance
import com.application.flickr.data.api.model.Resource
import com.application.flickr.data.api.model.Status
import com.application.flickr.data.livedata.AbsentLiveData
import com.application.flickr.data.livedata.WiseLiveData
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.data.repo.ImageRepository
import com.application.flickr.ui.common.BaseViewModel
import org.kodein.di.generic.instance

/**
 * Created by Harsh Jain on 19/04/19.
 */

class MainViewModel : BaseViewModel() {

    private val imageRepository: ImageRepository by kodeinInstance.instance()

    private var images: LiveData<Resource<List<UrlEntity>>>
    private val imagesResponse = WiseLiveData.create<Resource<List<UrlEntity>>>()
    private val imagesMutableLiveData = MutableLiveData<SearchParameters>()

    var isConnected: Boolean = false

    init {
        images = Transformations.switchMap(imagesMutableLiveData) {
            if (it != null) {
                imageRepository.getImages(it.searchTerm, it.page, isConnected)
            } else {
                AbsentLiveData.create<Resource<List<UrlEntity>>>()
            }
        }
        initTodoResponse()
    }

    internal fun getImagesForSearchTerm(
        searchTerm: String,
        page: Int
    ): LiveData<Resource<List<UrlEntity>>> {
        imagesMutableLiveData.value = SearchParameters(searchTerm, page)
        return imagesResponse
    }

    private inner class SearchParameters(val searchTerm: String, val page: Int)

    private fun initTodoResponse() {
        imagesResponse.addSource(images) { resource ->
            if (resource != null) {
                resource.data?.let {
                    when (resource.status) {
                        Status.SUCCESS -> imagesResponse.dispatchSuccess(Resource.success(it))
                        Status.ERROR -> {
                            imagesResponse.dispatchError(Resource.error(resource.errorMessage, it))
                        }
                        Status.LOADING -> imagesResponse.dispatchLoading(Resource.loading(it))
                    }
                }
            }
        }
    }
}