package com.garmin.garminkaptain.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.garmin.garminkaptain.data.UserReview
import com.garmin.garminkaptain.databinding.PoiReviewItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ReviewsListAdapter : ListAdapter<UserReview, ReviewsListAdapter.ReviewsListItemViewHolder>(ReviewDiffUtilCallback()) {
    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsListItemViewHolder {
        val binding = PoiReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewsListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewsListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ReviewsListItemViewHolder(binding: PoiReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val userName: TextView = binding.userNameView
        private val rating: RatingBar = binding.reviewRatingView
        private val ratingDetail: TextView = binding.reviewRatingView2
        private val reviewContent: TextView = binding.reviewContentView
        private val reviewDate: TextView = binding.reviewDateView
        private val reviewTitle: TextView = binding.reviewTitleView
        private val reviewPhoto: ImageView = binding.reviewPhoto

        fun bind(review: UserReview) {
            userName.text = review.username
            rating.rating = review.rating.toFloat()
            ratingDetail.text = String.format(NUMBER_FORMAT, review.rating)
            reviewTitle.text = review.title
            reviewDate.text = dateFormatter.format(review.date)
            reviewContent.text = review.review
            reviewPhoto.visibility = if (review.rating.toInt() % 2 == 0) View.GONE else View.VISIBLE
        }
    }

    private class ReviewDiffUtilCallback : DiffUtil.ItemCallback<UserReview>() {
        override fun areItemsTheSame(oldItem: UserReview, newItem: UserReview): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserReview, newItem: UserReview): Boolean =
            oldItem == newItem
    }

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
        private const val NUMBER_FORMAT = "%.2f"
    }
}