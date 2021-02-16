package com.tamil.graphqlapp.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tamil.graphqlapp.databinding.FragmentPostsDetailBinding
import com.tamil.graphqlapp.ext.showAToast
import com.tamil.graphqlapp.ui.posts.data.PostsAdapterItem
import com.tamil.graphqlapp.util.Event
import com.tamil.graphqlapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PostsDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostsDetailBinding
    private val args: PostsDetailFragmentArgs by navArgs()
    private var viewModel: PostsViewModel? = null

    private var postData: PostsAdapterItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postData = args.postData
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(PostsViewModel::class.java)
        postData?.let {
            binding.postTitle.text = it.title?.capitalize(Locale.getDefault())
            binding.postContent.text = it.content?.capitalize(Locale.getDefault())
            binding.postUser.text = "- ${it.name?.capitalize(Locale.getDefault())}"
        }
    }
}