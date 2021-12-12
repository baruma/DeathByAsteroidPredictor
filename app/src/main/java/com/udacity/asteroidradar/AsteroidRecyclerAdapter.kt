package com.udacity.asteroidradar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidCardBinding
import com.udacity.asteroidradar.detail.DetailFragment


// Extending a class gives more specialized behavior.  It also lets you override existing methods.
// This is an alternative to Inheritance or design patterns like Decorator.
//: RecyclerView.Adapter<AsteroidRecyclerAdapter.ViewHolder>(DiffCallback)

@BindingAdapter("asteroidsList")
fun bindRecylerView(recyclerView: RecyclerView, listOfAsteroids: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidRecyclerAdapter
    adapter.submitList(listOfAsteroids)
}

class AsteroidRecyclerAdapter(private val mList: List<Asteroid>) : ListAdapter<Asteroid, AsteroidRecyclerAdapter.AsteroidViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class AsteroidViewHolder(private var binding: AsteroidCardBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(asteroid: Asteroid) {
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements

            // TODO: BIND REST OF ELEMENTS HERE.
            binding.asteroidName.text = asteroid.codename
            binding.asteroidDate.text = asteroid.closeApproachDate.toString()

            if (asteroid.isPotentiallyHazardous == true) {
                binding.statusImage.setImageResource(R.drawable.ic_status_potentially_hazardous)
            } else {
                binding.statusImage.setImageResource(R.drawable.ic_status_normal)
            }

            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding: AsteroidCardBinding = inflate(LayoutInflater.from(parent.context), R.layout.asteroid_card, parent, false)

        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        val bundle = Bundle()
        bundle.putParcelable(DetailFragment.ARG_ASTEROID, asteroid)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_showDetail, bundle)
        }

        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick()
    }



}