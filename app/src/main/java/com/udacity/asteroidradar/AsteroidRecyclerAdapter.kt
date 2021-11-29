package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidCardBinding

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

    class AsteroidViewHolder(private var binding: AsteroidCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements

            binding.asteroidName.text = asteroid.codename
            binding.asteroidDate.text = asteroid.closeApproachDate.toString()

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding: AsteroidCardBinding = inflate(LayoutInflater.from(parent.context), R.layout.asteroid_card, parent, false)

        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}