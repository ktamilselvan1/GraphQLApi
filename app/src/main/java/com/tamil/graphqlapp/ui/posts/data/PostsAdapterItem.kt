package com.tamil.graphqlapp.ui.posts.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsAdapterItem(
    val id: String?,
    val title: String?,
    val content: String?,
    val userId: String?,
    var name: String? = null,
    var userName: String? = null
) : Parcelable
