package com.example.treespotter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import java.lang.RuntimeException

private const val TAG = "TREE_LIST_FRAGMENT"

class TreeListFragment : Fragment() {

    private lateinit var treeViewModel: TreeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // this line will fail if the Fragment is not attached to an activity,
        // and we need the application to get the TreeRepository.
        val application = requireActivity().application as TreeApplication

        treeViewModel = TreeViewModel.TreeViewModelFactory(application.treeRepository)
            .create(TreeViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val recyclerView = inflater.inflate(R.layout.fragment_tree_list, container, false)
        if (recyclerView !is RecyclerView) { throw RuntimeException("TreeListFragment View must be a RecyclerView") }

        val trees = listOf<Tree>()
        val adapter = TreeRecyclerViewAdapter(trees) { tree, isFavorite ->
            treeViewModel.setIsFavorite(tree, isFavorite)
        }

        treeViewModel.latestTrees.observe(requireActivity()) { treeList ->
            adapter.trees = treeList
            // To avoid updating everything, we can identify what has changed
            // https://firebase.google.com/docs/firestore/query-data/listen#view_changes_between_snapshots
            adapter.notifyDataSetChanged()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return recyclerView
    }


    companion object {
        @JvmStatic
        fun newInstance() = TreeListFragment()
    }
}