package com.snilloc.curiousbat.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.snilloc.curiousbat.MainActivity
import com.snilloc.curiousbat.R
import com.snilloc.curiousbat.databinding.FragmentRandomActivityBinding
import com.snilloc.curiousbat.model.RandomActivity
import com.snilloc.curiousbat.viewmodel.FavActivityViewModel
import com.snilloc.curiousbat.viewmodel.RandomActivityViewModel
import java.util.*

class RandomActivityFragment : Fragment(R.layout.fragment_random_activity) {
    private var _binding: FragmentRandomActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomActivityViewModel: RandomActivityViewModel
    private var progressDialog: Dialog? = null
    private lateinit var favActivityViewModel: FavActivityViewModel
    private lateinit var currentActivity: RandomActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomActivityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        favActivityViewModel = (activity as MainActivity)
        randomActivityViewModel = ViewModelProvider(this).get(RandomActivityViewModel::class.java)
        //Fetch the data
        randomActivityViewModel.getRandomActivityFromAPI()

        randomActivityObserver()

        binding.swipeRefresh.setOnRefreshListener {
            randomActivityViewModel.getRandomActivityFromAPI()
        }

        saveRandomActivity()
    }

    private fun saveRandomActivity() {
        binding.ivFavoriteAct.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_unselected)
        )
        var addedToFavourites = false
        binding.ivFavoriteAct.setOnClickListener {
            if (addedToFavourites) {
                Toast.makeText(activity, "You already added it to favourites",Toast.LENGTH_SHORT).show()
            } else {}
        }
    }

    private fun randomActivityObserver() {
        //Observe making the network request to show the progress dialog appropriately
        randomActivityViewModel.loadRandomActivity.observe(viewLifecycleOwner, { loadRandomAct ->
            loadRandomAct?.let {
                //loadRandomActivity variable if true, we are making the network call hence show the progress dialog
                if (loadRandomAct && !binding.swipeRefresh.isRefreshing) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        })

        //Observe the response from the network request
        randomActivityViewModel.randomActivityResponse.observe(viewLifecycleOwner, { randomActivity ->
                randomActivity?.let {
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    Log.d("TAG", "The response: $randomActivity")
                    binding.apply {
                        //Bind the data to the views
                        tvActivity.text = it.activity
                        tvAccessibility.text = it.accessibility.toString()
                        tvParticipants.text = it.participants.toString()
                        tvType.text = it.type
                        tvPrice.text = it.price.toString()

                        if (it.link.isNullOrBlank()) {
                            binding.btnSeeMoreDetails.visibility = View.GONE
                        } else {
                            binding.btnSeeMoreDetails.visibility = View.VISIBLE
                            showActivityDetails(it)
                        }
                        //Show random background colors
                        color()
                    }
                }
            })

        //Observe the errors
        randomActivityViewModel.randomActivityLoadError.observe(viewLifecycleOwner, {
            dataError->
            dataError?.let {
                Log.d("TAG", "RandomActivityObserver: $dataError")

                if (binding.swipeRefresh.isRefreshing) {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun showProgressDialog() {
        progressDialog = Dialog(requireActivity())
        progressDialog?.let {
            it.setContentView(R.layout.dialog_custom_progress)
            it.show()
        }
    }

    private fun color() {
        val random = Random()
        val color =
            Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

        binding.ivActImage.setBackgroundColor(color)
    }

    private fun showActivityDetails(randomActivity: RandomActivity) {
        //Pass the randomActivity selected to the DetailsFragment
        binding.btnSeeMoreDetails.setOnClickListener { mView ->
            val direction =
                RandomActivityFragmentDirections
                    .actionRandomActivityFragmentToDetailsFragment(randomActivity)
            mView.findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}