package com.example.travelwishlist_recyclerview

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelUnitTest {
    
    lateinit var vm: PlacesViewModel
    
    @Before
    fun clearViewModel() {
        vm = PlacesViewModel()
        vm.clear()
    }
    
    @Test
    fun add_newPlace_to_end() {
        val testPlace1 = Place("example1")
        val position1 = vm.addNewPlace(testPlace1)

        assertEquals(testPlace1, vm.placeAt(0))
        assertEquals(0, position1)

        val testPlace2 = Place("example2")
        val position2 = vm.addNewPlace(testPlace2)

        assertEquals(1, position2)
        assertEquals(testPlace1, vm.placeAt(0))
        assertEquals(testPlace2, vm.placeAt(1))

        val testPlace3 = Place("example3")
        val position3 = vm.addNewPlace(testPlace3)

        assertEquals(2, position3)
        assertEquals(testPlace1, vm.placeAt(0))
        assertEquals(testPlace2, vm.placeAt(1))
        assertEquals(testPlace3, vm.placeAt(2))
    }

    @Test
    fun test_reject_duplicatePlace_names() {
        val testPlace1 = Place("example1")
        val position1 = vm.addNewPlace(testPlace1)

        assertEquals(0, position1)
        assertEquals(testPlace1, vm.placeAt(0))

        val testPlace2 = Place("example1")
        val position2 = vm.addNewPlace(testPlace2)
        assertEquals(-1, position2)

        assertEquals(testPlace1, vm.placeAt(0))
        assertEquals(1, vm.getPlaces().size)

        val testPlace3 = Place("ExAmPle1")
        val position3 = vm.addNewPlace(testPlace3)

        assertEquals(-1, position3)
        assertEquals(testPlace1, vm.placeAt(0))
        assertEquals(1, vm.getPlaces().size)
    }

    @Test
    fun test_addPlace_atPosition() {
        val testPlace1 = Place("example1")
        val position1 = vm.addNewPlace(testPlace1)

        assertEquals(0, position1)
        assertEquals(testPlace1, vm.placeAt(0))

        val testPlace2 = Place("example2")
        val position2 = vm.addNewPlace( testPlace2, 0)

        assertEquals(0, position2)
        assertEquals(testPlace2, vm.placeAt(0))
        assertEquals(testPlace1, vm.placeAt(1))

        val testPlace3 = Place("example3")
        val position3 = vm.addNewPlace(testPlace3, 1)

        assertEquals(1, position3)
        assertEquals(testPlace2, vm.placeAt(0))
        assertEquals(testPlace3, vm.placeAt(1))
        assertEquals(testPlace1, vm.placeAt(2))

        val testPlace4 = Place("example4")
        val position4 = vm.addNewPlace(testPlace4, 1)

        assertEquals(1, position4)
        assertEquals(testPlace2, vm.placeAt(0))
        assertEquals(testPlace4, vm.placeAt(1))
        assertEquals(testPlace3, vm.placeAt(2))
        assertEquals(testPlace1, vm.placeAt(3))

        val testPlace5 = Place("example5")
        val position5 = vm.addNewPlace(testPlace5, 4)

        assertEquals(4, position5)
        assertEquals(testPlace2, vm.placeAt(0))
        assertEquals(testPlace4, vm.placeAt(1))
        assertEquals(testPlace3, vm.placeAt(2))
        assertEquals(testPlace1, vm.placeAt(3))
        assertEquals(testPlace5, vm.placeAt(4))
    }


    @Test
    fun testMovePlaceUp() {
        val testPlace1 = Place("example1")
        val testPlace2 = Place("example2")
        val testPlace3 = Place("example3")
        val testPlace4 = Place("example4")

        vm.addNewPlace(testPlace1)
        vm.addNewPlace(testPlace2)
        vm.addNewPlace(testPlace3)
        vm.addNewPlace(testPlace4)

        vm.movePlace(2, 1)
        assertEquals(listOf(testPlace1, testPlace3, testPlace2, testPlace4), vm.getPlaces())

        // move last to first
        vm.movePlace(3, 0)
        assertEquals(listOf(testPlace4, testPlace1, testPlace3, testPlace2), vm.getPlaces())

        // move middle to first
        vm.movePlace(2, 0)
        assertEquals(listOf(testPlace3, testPlace4, testPlace1, testPlace2), vm.getPlaces())

    }

    @Test
    fun testMovePlaceDown() {
        val testPlace1 = Place("A")
        val testPlace2 = Place("B")
        val testPlace3 = Place("C")
        val testPlace4 = Place("D")

        vm.addNewPlace(testPlace1)
        vm.addNewPlace(testPlace2)
        vm.addNewPlace(testPlace3)
        vm.addNewPlace(testPlace4)

        vm.movePlace(1, 2)
        assertEquals(listOf(testPlace1, testPlace3, testPlace2, testPlace4), vm.getPlaces())

        // move first to last
        vm.movePlace(0, 3)
        assertEquals(listOf(testPlace3, testPlace2, testPlace4, testPlace1), vm.getPlaces())
//        assertEquals(listOf(testPlace2, testPlace3, testPlace4, testPlace1), vm.getPlaces())

        // move middle to last
        vm.movePlace(2, 3)
        assertEquals(listOf(testPlace3, testPlace2, testPlace1, testPlace4), vm.getPlaces())

        // move middle to last
        vm.movePlace(1, 3)
        assertEquals(listOf(testPlace3, testPlace1, testPlace4, testPlace2), vm.getPlaces())

        // move start to middle
        vm.movePlace(0, 2)
        assertEquals(listOf(testPlace1, testPlace4, testPlace3, testPlace2), vm.getPlaces())

    }

    @Test
    fun testMovePlaceSamePosition() {

        val testPlace1 = Place("example1")
        val testPlace2 = Place("example2")
        val testPlace3 = Place("example3")
        val testPlace4 = Place("example4")

        vm.addNewPlace(testPlace1)
        vm.addNewPlace(testPlace2)
        vm.addNewPlace(testPlace3)
        vm.addNewPlace(testPlace4)

        vm.movePlace(0, 0)  // first
        assertEquals(listOf(testPlace1, testPlace2, testPlace3, testPlace4), vm.getPlaces())

        vm.movePlace(2, 2)  // middle
        assertEquals(listOf(testPlace1, testPlace2, testPlace3, testPlace4), vm.getPlaces())

        vm.movePlace(3, 3)  // last
        assertEquals(listOf(testPlace1, testPlace2, testPlace3, testPlace4), vm.getPlaces())
    }

    @Test
    fun testDeletePlace() {

        val testPlace1 = Place("example1")
        val testPlace2 = Place("example2")
        val testPlace3 = Place("example3")
        val testPlace4 = Place("example4")

        vm.addNewPlace(testPlace1)
        vm.addNewPlace(testPlace2)
        vm.addNewPlace(testPlace3)
        vm.addNewPlace(testPlace4)

        vm.deletePlace(1)  //middle
        assertEquals(listOf(testPlace1, testPlace3, testPlace4), vm.getPlaces())

        vm.deletePlace(0)  //start
        assertEquals(listOf(testPlace3, testPlace4), vm.getPlaces())

        vm.deletePlace(1)  //end
        assertEquals(listOf(testPlace3), vm.getPlaces())

        vm.deletePlace(0)  //last one
        assertEquals(listOf<Place>(), vm.getPlaces())
    }

}