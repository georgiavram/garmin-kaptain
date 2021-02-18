package com.garmin.garminkaptain.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.databinding.ReviewsListFragmentBinding
import com.garmin.garminkaptain.ui.details.adapter.ReviewsListAdapter
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiReviewsFragment : Fragment(R.layout.reviews_list_fragment) {
    private lateinit var binding: ReviewsListFragmentBinding
    private val reviewsListAdapter = ReviewsListAdapter()
    private val viewModel: PoiViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ReviewsListFragmentBinding.bind(view)
        binding.reviewsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = reviewsListAdapter
        }
        viewModel.getReviewsList().observe(viewLifecycleOwner, {
            reviewsListAdapter.submitList(it)
        })
    }
}