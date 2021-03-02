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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PoiDTO
import com.garmin.garminkaptain.databinding.PoiListFragmentBinding
import com.garmin.garminkaptain.ui.list.adapter.PoiListAdapter
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiListFragment : Fragment(R.layout.poi_list_fragment), PoiListAdapter.PoiListListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding: PoiListFragmentBinding
    private val poiListAdapter = PoiListAdapter(this)
    private var pointsOfInterest = mutableListOf<PoiDTO>()
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
                pointsOfInterest = it.toMutableList()
                poiListAdapter.submitList(it)
            })

        viewModel.getLoadingList()
            .observe(viewLifecycleOwner, {
                binding.swipeToRefresh.isRefreshing = it
            })
        val touchHandler = ItemTouchHelper(
            SwipeHandler(
                0,
                (ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            )
        )
        touchHandler.attachToRecyclerView(binding.poiList)
        activity?.getPreferences(Context.MODE_PRIVATE)?.registerOnSharedPreferenceChangeListener(this)
    }


    override fun seeDetails(position: Int) {
        val dto = pointsOfInterest[position]
        findNavController().navigate(
            PoiListFragmentDirections.actionPoiListFragmentToPoiDetailsFragment(dto.poi.id)
        )
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        Log.d(
            TAG, "onSharedPreferenceChanged " +
                    "key: $key value: ${sharedPreferences.getInt(key, 0)}"
        )
    }

    inner class SwipeHandler(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val poi = pointsOfInterest[viewHolder.adapterPosition]
            viewModel.deletePoi(poi.poi.id)
        }

    }
}