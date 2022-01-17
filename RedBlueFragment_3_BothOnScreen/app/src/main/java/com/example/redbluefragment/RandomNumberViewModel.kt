package com.example.redbluefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RandomNumberViewModel: ViewModel() {
   val randomNumber = MutableLiveData(0)
}

