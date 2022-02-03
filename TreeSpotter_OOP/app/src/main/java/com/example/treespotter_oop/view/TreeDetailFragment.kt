package com.example.treespotter_oop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.treespotter_oop.R
import com.example.treespotter_oop.TreeViewModel
import com.example.treespotter_oop.model.Maple

class TreeDetailFragment : Fragment() {

    private var treeId: Int = 0

    private val treeViewModel: TreeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TreeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            treeId = it.getInt(TREE_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tree_detail, container, false)

        treeViewModel.getTreeWithId(treeId)?.let { tree ->

            view.findViewById<TextView>(R.id.tree_name).text = tree.name
            view.findViewById<TextView>(R.id.state).text = tree.state

            view.findViewById<ImageView>(R.id.tree_image)
                .setImageDrawable(ContextCompat.getDrawable(requireActivity(), tree.imageResourceId))

            val switch = view.findViewById<SwitchCompat>(R.id.detail_spotted_this_tree)

            switch.isChecked = tree.spotted

            switch.setOnCheckedChangeListener { compoundButton, isChecked ->
                treeViewModel.setSpotted(treeId, isChecked)
            }

            val treeIcon = view.findViewById<ImageView>(R.id.detail_tree_icon)
            if (tree is Maple) {
                    treeIcon.visibility = View.VISIBLE
            } else {
                    treeIcon.visibility = View.GONE
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param state
         * @return A new instance of fragment TreeDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(treeId: Int) =
            TreeDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(TREE_ID_KEY, treeId)
                }
            }
    }
}