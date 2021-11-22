package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidCardBinding

// Extending a class gives more specialized behavior.  It also lets you override existing methods.
// This is an alternative to Inheritance or design patterns like Decorator.
//: RecyclerView.Adapter<AsteroidRecyclerAdapter.ViewHolder>(DiffCallback)

class AsteroidRecyclerAdapter(private val mList: List<Asteroid>) : RecyclerView.Adapter<AsteroidRecyclerAdapter.AsteroidViewHolder>() {

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
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding: AsteroidCardBinding = inflate(LayoutInflater.from(parent.context), R.layout.asteroid_card, parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    // return  number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

}