package com.example.maps_and_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maps_and_location.databinding.LocationListFragmentBinding

/**
 * A placeholder fragment containing a simple view.
 */
class LocationListFragment : Fragment(), AttractionListItemInteractionListener {

    private lateinit var pageViewModel: AttractionsViewModel
    private var _binding: LocationListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(requireActivity()).get(AttractionsViewModel::class.java).apply {
//            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LocationListFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.locationListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerAdapter = RecyclerAdapter(pageViewModel.getAttractions(), this)
        binding.locationListRecyclerView.adapter = recyclerAdapter

        pageViewModel.attractionsList.observe(viewLifecycleOwner) {
            recyclerAdapter.notifyDataSetChanged()
        }

        return root
    }

    override fun onAttractionVisited(attractionId: Int, visited: Boolean) {
        // geographically, is this person at the attraction?
        pageViewModel.setVisited(attractionId, visited)
    }

    override fun onAttractionSelected(attractionId: Int) {
        TODO("go to map")

    }


    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): LocationListFragment {
            return LocationListFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Remove reference to data binding to avoid memory leaks TODO
    }


}