package com.example.treespotter_oop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.setFragmentResultListener(TREE_SELECTED_FRAGMENT_RESULT, this) {
            requestKey, bundle ->
            when (requestKey) {
                TREE_SELECTED_FRAGMENT_RESULT -> bundle.getInt(TREE_ID_KEY).let { treeId ->
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainerView,
                            TreeDetailFragment.newInstance(treeId),
                            "DETAILS"
                        )
                        .addToBackStack("DETAILS")
                        .commit()
                }
            }
        }
    }
}