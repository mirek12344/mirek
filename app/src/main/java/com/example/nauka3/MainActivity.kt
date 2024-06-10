package com.example.nauka3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val historyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightSpinner: Spinner = findViewById(R.id.spinner2)
        val heightSpinner: Spinner = findViewById(R.id.spinner3)
        val ageSpinner: Spinner = findViewById(R.id.spinner)
        val calculateButton: Button = findViewById(R.id.button)
        val historyButton: Button = findViewById(R.id.historyButton) // New History button

        // Setup the spinners (this can be done with real data or a static list)
        val weights = ArrayList<Int>()
        for (i in 30..200) weights.add(i)
        val heights = ArrayList<Int>()
        for (i in 100..250) heights.add(i)
        val ages = ArrayList<Int>()
        for (i in 1..120) ages.add(i)

        val weightAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, weights)
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        weightSpinner.adapter = weightAdapter

        val heightAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, heights)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        heightSpinner.adapter = heightAdapter

        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ages)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter

        // Calculate button onClick listener
        calculateButton.setOnClickListener {
            val weight = weightSpinner.selectedItem as Int
            val height = heightSpinner.selectedItem as Int

            val bmi = calculateBMI(weight, height)
            val category = bmiCategory(bmi)

            val resultMessage = "Twoje BMI wynosi: %.2f\nKategoria: %s".format(bmi, category)
            Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show()

            // Add result to history
            historyList.add(resultMessage)
        }

        // History button onClick listener
        historyButton.setOnClickListener {
            val historyMessage = historyList.joinToString("\n\n")
            AlertDialog.Builder(this)
                .setTitle("Historia obliczeń")
                .setMessage(historyMessage)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun calculateBMI(weight: Int, height: Int): Double {
        val heightInMeters = height / 100.0
        return weight / (heightInMeters * heightInMeters)
    }

    private fun bmiCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Niedowaga"
            bmi in 18.5..24.9 -> "Normalna waga"
            bmi in 25.0..29.9 -> "Nadwaga"
            else -> "Otyłość"
        }
    }
}
