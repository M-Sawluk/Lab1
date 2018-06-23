package com.lab2.michau.lab2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var textToShow = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNameValidation()
        setSurnameValidation()
        setGradesValidation()
        setButtonListener()
        setSummary()
        restoredData()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("name", name.text.toString())
        outState?.putString("surname", surname.text.toString())
        outState?.putString("number", oceny.text.toString())
        getSharedPreferences("data", Context.MODE_PRIVATE)
                .edit()
                .putString("name", name.text.toString())
                .putString("surname", surname.text.toString())
                .putString("number", oceny.text.toString())
                .apply()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        name.setText(savedInstanceState?.get("name").toString())
        surname.setText(savedInstanceState?.get("surname").toString())
        oceny.setText(savedInstanceState?.get("number").toString())
    }

    private fun showToast() {
        if (textToShow.isNotBlank()) {
            val toast = Toast.makeText(this, textToShow, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, -50)
            toast.show()
            textToShow = ""
        }
    }

    private fun setNameValidation() {
        name.setOnFocusChangeListener { view, b ->
            textToShow = if (name.text.isBlank()) {
                showToast()
                getString(R.string.name_empt)
            } else {
                ""
            }
        }
        name.setOnClickListener {
            if (name.text.isBlank()) textToShow = getString(R.string.name_empt)
        }
    }

    private fun setSurnameValidation() {
        surname.setOnFocusChangeListener { view, b ->
            textToShow = if (surname.text.isBlank()) {
                showToast()
                getString(R.string.surname_empty)
            } else {
                ""
            }
        }

        surname.setOnClickListener {
            if (name.text.isBlank()) textToShow = getString(R.string.surname_empty)
        }
    }

    private fun setGradesValidation() {
        oceny.setOnFocusChangeListener { view, b ->
            var numberOfGrades = oceny.text.toString()
            textToShow = if (numberOfGrades.isBlank() || Integer.parseInt(numberOfGrades) <= 5 || Integer.parseInt(numberOfGrades) >= 15) {
                showToast()
                getString(R.string.grades_range_wrong)
            } else {
                ""
            }
        }
        oceny.setOnClickListener {
            if (name.text.isBlank()) textToShow = getString(R.string.grades_range_wrong)
        }
    }

    private fun setButtonListener() {
        gradeButton.setOnClickListener {
            var numberOfGrades = oceny.text.toString()
            if (name.text.isNotBlank() && surname.text.isNotBlank() && numberOfGrades.isNotBlank()
                    && Integer.parseInt(numberOfGrades) >= 5 && Integer.parseInt(numberOfGrades) <= 15) {
                val gradesIntent = Intent(this, GradesActivity::class.java)
                gradesIntent.putExtra("NUMBER_OF_GRADES", Integer.parseInt(oceny.text.toString()))
                startActivity(gradesIntent)
            } else {
                textToShow = "Jedno z pól jest źle wypełnione"
                showToast()
            }
        }
    }

    private fun setSummary() {
        val sum = intent.getDoubleExtra("SUM", 0.0)
        if (sum > 0.0) {
            summary.visibility = View.VISIBLE
            if (sum >= 3.6) {
                gradeButton.text = getString(R.string.superString)
                gradeButton.setOnClickListener {
                    textToShow = getString(R.string.gratz)
                    showToast()
                    finish()
                }
            } else {
                gradeButton.text = getString(R.string.sorry)
                gradeButton.setOnClickListener {
                    textToShow = getString(R.string.failed)
                    showToast()
                    finish()
                }
            }
            summary.text = getString(R.string.yourAvr) + sum.toString()
            oceny.isEnabled = false
            surname.isEnabled = false
            name.isEnabled = false
        }
    }

    private fun restoredData() {
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        name.setText(sharedPreferences.getString("name", ""))
        surname.setText(sharedPreferences.getString("surname", ""))
        oceny.setText(sharedPreferences.getString("number", ""))

        sharedPreferences
                .edit()
                .clear()
                .apply()
    }
}