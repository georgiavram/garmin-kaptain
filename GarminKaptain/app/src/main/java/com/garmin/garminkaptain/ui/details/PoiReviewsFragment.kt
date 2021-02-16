package com.garmin.garminkaptain.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.databinding.ReviewsListFragmentBinding
import com.garmin.garminkaptain.ui.details.adapter.ReviewsListAdapter

class PoiReviewsFragment : Fragment(R.layout.reviews_list_fragment) {
    private lateinit var binding: ReviewsListFragmentBinding
    private val reviewsListAdapter = ReviewsListAdapter()
    private val args: PoiReviewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ReviewsListFragmentBinding.bind(view)
        val reviewsList = args.reviewsList.toList()
        binding.reviewsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = reviewsListAdapter
        }
        reviewsListAdapter.submitList(reviewsList)
    }
}