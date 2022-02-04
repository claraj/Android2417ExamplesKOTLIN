package com.example.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.content.res.AppCompatResources
import com.example.viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var animalsDrawables = mapOf(
        R.id.kitten_radio to R.drawable.kitten,
        R.id.puppy_radio to R.drawable.puppy,
        R.id.lizard_radio to R.drawable.lizard,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.kittenRadio.isChecked = true
        binding.selectionTextView.text = getString(R.string.kitten)

        binding.animalRadioGroup.setOnCheckedChangeListener { radioGroup, selectedRadioId ->
            animalsDrawables[selectedRadioId]?.let { drawableId ->
                setImage(drawableId)
                val radioButton = findViewById<RadioButton>(selectedRadioId)
                binding.selectionTextView.text = radioButton.text
            }
        }
    }

    private fun setImage(drawableId: Int) {
        val drawable = AppCompatResources.getDrawable(this, drawableId)
        binding.animalImage.setImageDrawable(drawable)
    }
}