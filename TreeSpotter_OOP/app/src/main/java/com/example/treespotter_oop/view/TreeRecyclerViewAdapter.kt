package com.example.treespotter_oop

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.treespotter_oop.model.Tree

interface TreeListListener {
    fun treeSelected(treeId: Int)
    fun treeSpottedChanged(treeId: Int, spotted: Boolean)
}


class TreeRecyclerViewAdapter(
    var trees: List<Tree>,
    private val treeListListener: TreeListListener
) : RecyclerView.Adapter<TreeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_tree_list_item, parent, false), parent.context
            )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tree = trees[position]
        holder.bind(tree)
    }

    override fun getItemCount(): Int = trees.size

    inner class ViewHolder(itemView: View, val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(tree: Tree) {

            itemView.findViewById<TextView>(R.id.state).text = tree.state
            itemView.findViewById<TextView>(R.id.tree_name).text = tree.name
            itemView.findViewById<ImageView>(R.id.thumbnail).setImageDrawable(ContextCompat.getDrawable(context, tree.thumbnailResourceId))
            itemView.findViewById<TextView>(R.id.tree_name).text = tree.name

            itemView.setOnClickListener {   // event handler for tapping the list view
                treeListListener.treeSelected(tree.id)
            }

            itemView.findViewById<CheckBox>(R.id.list_spotted_checkbox).apply {
                isChecked = tree.spotted
                setOnClickListener {
                    treeListListener.treeSpottedChanged(tree.id, isChecked) }
            }
        }

    }

}