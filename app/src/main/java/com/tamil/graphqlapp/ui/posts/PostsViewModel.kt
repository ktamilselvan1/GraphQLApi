package com.tamil.graphqlapp.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.tamil.PostsListQuery
import com.tamil.graphqlapp.ui.posts.data.PostsAdapterItem
import com.tamil.graphqlapp.util.Event
import com.tamil.graphqlapp.util.Resource
import com.tamil.type.PageQueryOptions
import com.tamil.type.PaginateOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val apolloClient: ApolloClient) : ViewModel() {

    private val _postsData = MutableLiveData<Event<Resource<List<PostsAdapterItem>>>>()
    val postsData: LiveData<Event<Resource<List<PostsAdapterItem>>>> = _postsData

    val posts = mutableListOf<PostsAdapterItem>()
    var currentPage: Int = 1
    private var totalPages = 0

    fun getPosts() {
        if (totalPages != 0 && currentPage == totalPages)
            return
        viewModelScope.launch {
            val response = try {
                val queryOption = PageQueryOptions(
                    Input.optional(
                        PaginateOptions(
                            page = Input.optional(currentPage),
                            limit = Input.optional(PAGE_LIMIT)
                        )
                    )
                )
                apolloClient.query(PostsListQuery(queryOption)).await()
            } catch (e: ApolloException) {
                // handle protocol errors
                _postsData.postValue(Event(Resource.error("Something went wrong", null)))
                return@launch
            }
            if (response.hasErrors()) {
                // handle application errors
                _postsData.postValue(Event(Resource.error("Something went wrong", null)))
                return@launch
            }
            val responseData: PostsListQuery.Posts? = response.data?.posts
            responseData?.meta?.totalCount?.let {
                totalPages = it
            }
            currentPage++
            responseData?.data?.map {
                it?.let { data ->
                    posts.add(
                        PostsAdapterItem(
                            data.id,
                            data.title,
                            data.body,
                            data.user?.id,
                            data.user?.name,
                            data.user?.username
                        )
                    )
                }
            }
            _postsData.postValue(Event(Resource.success(posts)))
        }
    }

    companion object {
        private const val PAGE_LIMIT = 10
    }
}