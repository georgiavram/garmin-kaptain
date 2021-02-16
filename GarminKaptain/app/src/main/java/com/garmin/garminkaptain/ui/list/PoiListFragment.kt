package com.garmin.garminkaptain.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.data.poiList
import com.garmin.garminkaptain.databinding.PoiListFragmentBinding
import com.garmin.garminkaptain.ui.list.adapter.PoiListAdapter

class PoiListFragment : Fragment(R.layout.poi_list_fragment), PoiListAdapter.PoiListListener {
    private lateinit var binding: PoiListFragmentBinding
    private val poiListAdapter = PoiListAdapter(this)
    private val pointsOfInterest = poiList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = PoiListFragmentBinding.bind(view)
        binding.poiList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = poiListAdapter
        }
        poiListAdapter.submitList(pointsOfInterest)
    }

    override fun seeDetails(position: Int) {
        val poi = pointsOfInterest[position]
        findNavController().navigate(
            PoiListFragmentDirections.actionPoiListFragmentToPoiDetailsFragment(poi.id)
        )
    }
}