package com.example.treespotter_oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

/**
 * A fragment representing a list of Trees.
 */

const val TREE_SELECTED_FRAGMENT_RESULT = "tree selected result"
const val TREE_ID_KEY = "tree id bundle key"

class TreeListFragment : Fragment(), TreeListListener {

    private val treeViewModel: TreeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TreeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val recyclerView = inflater.inflate(R.layout.fragment_tree_list, container, false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TreeRecyclerViewAdapter(treeViewModel.trees.value ?: listOf<Tree>(), this)
        recyclerView.adapter = adapter

        treeViewModel.trees.observe(requireActivity()) { trees ->
            adapter.trees = trees
            adapter.notifyDataSetChanged()
        }

        return recyclerView
    }

    override fun treeSelected(treeId: Int) {
        Bundle().apply {
            putInt(TREE_ID_KEY, treeId)
            parentFragmentManager.setFragmentResult(TREE_SELECTED_FRAGMENT_RESULT, this)
        }
    }

    override fun treeSpottedChanged(treeId: Int, spotted: Boolean) {
        treeViewModel.setSpotted(treeId, spotted)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TreeListFragment()
    }
}