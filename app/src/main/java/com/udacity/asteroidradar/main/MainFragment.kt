package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private lateinit var asteroidAdapter: AsteroidRecyclerAdapter

    private lateinit var binding: FragmentMainBinding

    private lateinit var mainViewModel: MainViewModel

    // step 1
    private val observer: Observer<PictureOfDay> =
        Observer<PictureOfDay> { pictureOfTheDay ->
           Picasso.with(context).load(pictureOfTheDay.url).into(binding.activityMainImageOfTheDay)
        }

    // If I'm inflating the entire fragment is it necessary to inflate the RecyclerView as well
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application
        val datasource = AsteroidDatabase.getDatabase(application).asteroidDAO
        val viewModelFactory = MainViewModelFactory(datasource, application)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        asteroidAdapter = AsteroidRecyclerAdapter(mutableListOf())
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        binding.asteroidRecycler.adapter = asteroidAdapter

        setHasOptionsMenu(true)

        return binding.root
    }

    // step 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.pictureResponse.observe(binding.lifecycleOwner!!, observer)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

}


