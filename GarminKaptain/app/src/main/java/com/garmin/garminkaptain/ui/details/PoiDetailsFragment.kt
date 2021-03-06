package com.garmin.garminkaptain.ui.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.Resource
import com.garmin.garminkaptain.data.ReviewSummary
import com.garmin.garminkaptain.databinding.PoiDetailsFragment2Binding
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiDetailsFragment : Fragment() {
    private lateinit var binding: PoiDetailsFragment2Binding
    private val args: PoiDetailsFragmentArgs by navArgs()

    // Creates a single ViewModel per activity
//    private val viewModel: PoiViewModel by activityViewModels()

    // Creates a different ViewModel for each Fragment
    private val viewModel: PoiViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
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

        viewModel.getLoading().observe(
            viewLifecycleOwner, {
                binding.poiProgress.visibility = if (it) VISIBLE else GONE
            })

        viewModel.getPoiSummary(args.poiId)
            .observe(viewLifecycleOwner, { resource ->
                resource.let {
                    when (resource) {
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            showProgressIndicator()
                        }
                        is Resource.Success -> {
                            hideProgressIndicator()
                            resource.data.let {
                                val summary: ReviewSummary = it as ReviewSummary
                                binding.apply {
                                    poiDetailsGroup.visibility = VISIBLE
                                    poiRatingView.rating = summary.averageRating.toFloat()
                                    poiNumReviewsView.text = getString(R.string.label_num_reviews, summary.numberOfReviews)
                                    poiViewReviewsButton.isEnabled = summary.numberOfReviews > 0
                                }
                            }
                        }
                    }
                }
            })

        viewModel.getPoi(args.poiId)
            .observe(viewLifecycleOwner, { dto ->
                println(dto.toString())
                dto?.let {
                    binding.apply {
                        poiNameView.text = dto.poi.name
                        poiTypeView.text = dto.poi.poiType
                        poiViewReviewsButton.setOnClickListener {
                            findNavController().navigate(
                                PoiDetailsFragmentDirections.actionPoiDetailsFragmentToPoiReviewsFragment(args.poiId)
                            )
                        }
                    }
                }
            })
    }

    private fun showProgressIndicator() {
        binding.poiProgress.visibility = VISIBLE
    }

    private fun hideProgressIndicator() {
        binding.poiProgress.visibility = GONE
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