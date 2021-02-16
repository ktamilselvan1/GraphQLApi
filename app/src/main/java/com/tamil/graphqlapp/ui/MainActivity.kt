package com.tamil.graphqlapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.tamil.PostsListQuery
import com.tamil.graphqlapp.R
import com.tamil.graphqlapp.databinding.ActivityMainBinding
import com.tamil.graphqlapp.ui.posts.PostsFragmentFactory
import com.tamil.type.PageQueryOptions
import com.tamil.type.PaginateOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var postsFragmentFactory: PostsFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = postsFragmentFactory
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        NavigationUI.setupWithNavController(
            binding.toolbar,
            findNavController(R.id.nav_host_fragment)
        )
    }
}