package com.garmin.garminkaptain.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.databinding.ReviewsListFragmentBinding
import com.garmin.garminkaptain.ui.details.adapter.ReviewsListAdapter
import com.garmin.garminkaptain.viewModel.ReviewViewModel

class PoiReviewsFragment : Fragment(R.layout.reviews_list_fragment) {
    private lateinit var binding: ReviewsListFragmentBinding
    private val reviewsListAdapter = ReviewsListAdapter()
    private val viewModel: ReviewViewModel by viewModels()
    private val args: PoiReviewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ReviewsListFragmentBinding.bind(view)
        binding.reviewsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = reviewsListAdapter
        }
        viewModel.getReviews(args.poiId)
        viewModel.reviewLiveData
            .observe(viewLifecycleOwner, {
                reviewsListAdapter.submitList(it)
            })
    }
}