package com.example.treespotter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private const val TAG = "TREE_VIEW_MODEL"

class TreeViewModel(private val treeRepository: TreeRepository): ViewModel() {

    // Provide methods that Fragments, Activities can call
    // and pass requests onto the TreeRepository.
    // No Firebase-related code, or any code that specifically refers
    // to any data store.

    val latestTrees = MutableLiveData<List<Tree>>()

    init {
        treeRepository.observeTrees { trees ->
            latestTrees.postValue(trees)
        }
    }

    fun addTree(tree: Tree) {
        treeRepository.addTree(tree)
    }

    override fun onCleared() {
        super.onCleared()
        treeRepository.stopObservingTrees()
        Log.d(TAG, "Removing listener")
    }

    fun deleteTree(tree: Tree) {
        treeRepository.deleteTree(tree)
    }

    fun setIsFavorite(tree: Tree, favorite: Boolean) {
        treeRepository.setIsFavorite(tree, favorite)
    }

    class TreeViewModelFactory(private val treeRepository: TreeRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TreeViewModel::class.java)) {
                return TreeViewModel(treeRepository) as T
            }
            throw IllegalArgumentException("$modelClass needs to be a TreeRepository")

        }
    }

}