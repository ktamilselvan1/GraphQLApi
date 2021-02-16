package com.tamil.graphqlapp.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tamil.graphqlapp.databinding.FragmentPostsListBinding
import com.tamil.graphqlapp.ext.showAToast
import com.tamil.graphqlapp.ui.adapters.PostsAdapter
import com.tamil.graphqlapp.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsListFragment : Fragment() {
    private lateinit var binding: FragmentPostsListBinding
    private var viewModel: PostsViewModel? = null
    private val postsAdapter = PostsAdapter()
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postsRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = postsAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(PostsViewModel::class.java)
        registerForObservers()
        viewModel?.getPosts()
    }

    private fun registerForObservers() {
        binding.postsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        isLoading = true
                        viewModel?.getPosts()
                    }
                }
            }
        })
        postsAdapter.setOnItemClickListener { data, view ->
            val action =
                PostsListFragmentDirections.actionPostsListFragmentToPostsDetailFragment(data)
            findNavController().navigate(action)
        }
        viewModel?.postsData?.observe(viewLifecycleOwner) { response ->
            response?.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        resource.data?.let {
                            postsAdapter.apply {
                                posts = it
                                notifyDataSetChanged()
                            }
                        }
                        isLoading = false
                    }
                    Status.ERROR -> {
                        isLoading = false
                        binding.root.showAToast(resource.message ?: "Something went wrong")
                    }
                }
            }
        }
    }
}