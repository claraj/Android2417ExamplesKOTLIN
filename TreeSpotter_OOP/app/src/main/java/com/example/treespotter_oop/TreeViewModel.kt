package com.example.treespotter_oop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.treespotter_oop.model.Maple
import com.example.treespotter_oop.model.Tree

class TreeViewModel: ViewModel() {
val name = MutableLiveData("list")

     val trees = MutableLiveData(listOf(
        Tree(
            1,
            "Eastern White Pine",
            "MI",
            R.drawable.eastern_white_pine,
            R.drawable.eastern_white_pine_thumbnail),
        Tree(
            2,
            "Red Pine",
            "MN",
            R.drawable.red_pine,
            R.drawable.red_pine_thumbnail),
        Maple(
            3,
            "Sugar Maple",
            "WI",
            R.drawable.sugar_maple,
            R.drawable.sugar_maple_thumbnail)
        )
     )

    fun getTreeWithId(treeId: Int): Tree? {
        return trees.value?.find { tree -> tree.id == treeId }
    }

    fun setSpotted(treeId: Int, spotted: Boolean) {
        val tree = getTreeWithId(treeId)
        tree?.spotted = spotted
        trees.value = trees.value   // Change the value to itself to make trees notify its observers
    }

}
