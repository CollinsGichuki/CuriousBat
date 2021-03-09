package com.snilloc.curiousbat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.snilloc.curiousbat.R
import com.snilloc.curiousbat.databinding.FragmentDetailsBinding
import com.snilloc.curiousbat.model.RandomActivity

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var currentActivity: RandomActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RandomActivty passed through navArgs
        currentActivity = args.randomActivity!!

        binding.webView.apply {
            webViewClient = WebViewClient()
            //Load the url from the args passed onto the webView
            //Link can be null
            currentActivity.link?.let { loadUrl(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}