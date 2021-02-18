package com.garmin.garminkaptain.ui.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.poiList
import com.garmin.garminkaptain.databinding.PoiDetailsFragment2Binding
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiDetailsFragment : Fragment() {
    private lateinit var binding: PoiDetailsFragment2Binding
    private val args: PoiDetailsFragmentArgs by navArgs()
    private val viewModel: PoiViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: called")
        return inflater.inflate(R.layout.poi_details_fragment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: called")
        binding = PoiDetailsFragment2Binding.bind(view)

        viewModel.getPoi(args.poiId).observe(viewLifecycleOwner, { poi ->
            poi?.let {
                binding.apply {
                    poiNameView.text = poi.name
                    poiTypeView.text = poi.poiType
                    poiRatingView.rating = poi.reviewSummary.averageRating.toFloat()
                    poiNumReviewsView.text =
                        getString(R.string.label_num_reviews, poi.reviewSummary.numberOfReviews)
                    poiViewReviewsButton.isEnabled = poi.reviewSummary.numberOfReviews > 0
                    poiViewReviewsButton.setOnClickListener {
                        findNavController().navigate(
                            PoiDetailsFragmentDirections.actionPoiDetailsFragmentToPoiReviewsFragment(poi.userReviews.toTypedArray())
                        )
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach() called")
    }
}