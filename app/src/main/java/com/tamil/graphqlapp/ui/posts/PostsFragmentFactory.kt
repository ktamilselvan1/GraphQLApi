package com.tamil.graphqlapp.ui.posts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class PostsFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PostsListFragment::class.java.name -> PostsListFragment()
            PostsDetailFragment::class.java.name -> PostsDetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}