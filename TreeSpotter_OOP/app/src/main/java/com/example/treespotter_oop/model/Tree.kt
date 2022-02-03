package com.example.treespotter_oop.model

import java.util.*

open class Tree(val id: Int, val name: String, val state: String, val imageResourceId: Int, val thumbnailResourceId: Int) {

//    private val isMapleTree: Boolean = (name == "Sugar Maple")

    init {
        if (name.isBlank() or state.isBlank()) {
            throw Exception("Tree and state MUST be provided!")
        }
    }

    var spotted = false   // Assume user has not spotted this tree yet
        set(value) {
            field = value // this sets the value
            if (spotted) {
                dateSpotted = Date()
            }
            else {
                dateSpotted = null
            }
        }

    val uppercaseState: String
        get() {
            return state.uppercase()
        }

    val spottedDaysAgo: Long?
        get() {
            val ms = dateSpotted?.time ?: return null
            return (Date().time - ms ) / (24 * 60 * 60 * 1000)
        }

    var dateSpotted: Date? = null
        get() {
            // todo whatever you need to do before returning the value
            return field
        }
        set(value) {
            // todo whatever you need to do before the field is set to the new value
            field = value
            // todo whatever you need to do after the field is set to the new value
        }

    override fun toString(): String {
        return "$state tree is $name, spotted $spotted"
    }

//    fun makeMapleSyrup() {
//        if (isMapleTree) {
//            println("OK! If it's spring")
//        } else {
//            println("Wrong kind of tree")
//        }
//    }
}







//import java.util.*
//class Tree(val name: String,
//           val state: String,
//           val webPage: String,
//           val drawableResource: Int) {
//
//     var spotted: Boolean = false
//        set(value){
//            field = value
//
//            println("hello")
//            if (spotted) {
//                dateSpotted = Date() }
//            else { dateSpotted = null }
//
//
//        }
//
//    var dateSpotted: Date? = null
//
//}