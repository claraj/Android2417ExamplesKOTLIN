package com.example.treespotter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val TAG = "TREE_RECYCLER_ADAPTER"

class TreeRecyclerViewAdapter(var trees: List<Tree>) :
    RecyclerView.Adapter<TreeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val treeNameView: TextView = view.findViewById(R.id.tree_name)
        val dateSpottedView: TextView = view.findViewById(R.id.date_spotted)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_tree_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tree = trees[position]
        holder.treeNameView.text = tree.name
        holder.dateSpottedView.text = tree.dateSpotted.toString()
    }


    override fun getItemCount(): Int {
        return trees.size
    }

}