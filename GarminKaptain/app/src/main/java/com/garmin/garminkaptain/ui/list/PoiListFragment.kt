package com.garmin.garminkaptain.ui.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.databinding.PoiListFragmentBinding
import com.garmin.garminkaptain.ui.list.adapter.PoiListAdapter
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiListFragment : Fragment(R.layout.poi_list_fragment), PoiListAdapter.PoiListListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding: PoiListFragmentBinding
    private val poiListAdapter = PoiListAdapter(this)
    private var pointsOfInterest = listOf<PointOfInterest>()
    private val viewModel: PoiViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.supportActionBar?.show()

        binding = PoiListFragmentBinding.bind(view)
        binding.poiList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = poiListAdapter
        }

        binding.swipeToRefresh.setOnRefreshListener { viewModel.loadPoiList() }

        viewModel.getPoiList()
            .observe(viewLifecycleOwner, {
                pointsOfInterest = it
                poiListAdapter.submitList(it)
            })

        viewModel.getLoadingList()
            .observe(viewLifecycleOwner, {
                binding.swipeToRefresh.isRefreshing = it
            })

        activity?.getPreferences(Context.MODE_PRIVATE)?.registerOnSharedPreferenceChangeListener(this)
    }


    override fun seeDetails(position: Int) {
        val poi = pointsOfInterest[position]
        findNavController().navigate(
            PoiListFragmentDirections.actionPoiListFragmentToPoiDetailsFragment(poi.id)
        )
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        Log.d(
            TAG, "onSharedPreferenceChanged " +
                    "key: $key value: ${sharedPreferences.getInt(key, 0)}"
        )
    }
}