package com.garmin.garminkaptain.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.garmin.garminkaptain.data.PoiDTO
import com.garmin.garminkaptain.databinding.PoiListItemBinding

class PoiListAdapter(private val listener: PoiListListener) : ListAdapter<PoiDTO, PoiListAdapter.PoiListItemViewHolder>(PoiDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiListItemViewHolder {
        val binding = PoiListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PoiListItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PoiListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PoiListItemViewHolder(private val binding: PoiListItemBinding, private val listener: PoiListListener) : RecyclerView.ViewHolder(binding.root) {
        private val poiItemName: TextView = binding.poiItemNameView
        private val poiItemType: TextView = binding.poiItemTypeView

        init {
            this.binding.root.setOnClickListener {
                listener.seeDetails(adapterPosition)
            }
        }

        fun bind(pointOfInterest: PoiDTO) {
            poiItemName.text = pointOfInterest.poi.name
            poiItemType.text = pointOfInterest.poi.poiType
        }
    }

    private class PoiDiffUtilCallback : DiffUtil.ItemCallback<PoiDTO>() {

        override fun areItemsTheSame(oldItem: PoiDTO, newItem: PoiDTO) = oldItem.poi.id == newItem.poi.id

        override fun areContentsTheSame(oldItem: PoiDTO, newItem: PoiDTO) = oldItem == newItem
    }

    /**
     *  Abstract type responsible for handling events from Point of Interest list.
     */
    interface PoiListListener {
        /**
         * See the details of the selected point of interest.
         *
         * @param position Int Represents the position of the selected point of interest.
         */
        fun seeDetails(position: Int)
    }
}