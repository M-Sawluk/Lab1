package com.lab2.michau.lab2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lab2.michau.lab2.R.id.list
import com.lab2.michau.lab2.adapter.GradesAdapter
import com.lab2.michau.lab2.model.Grade
import kotlinx.android.synthetic.main.activity_grades.*
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class GradesActivity : AppCompatActivity() {
    private val gradesDefaultValue = 2
    private val gradesKey = "NUMBER_OF_GRADES"
    private val grade = "Ocena "
    private val sumKey = "SUM"
    private val grades : MutableList<Grade> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grades)
        val numberOfGrades = intent.getIntExtra(gradesKey, gradesDefaultValue)
        fillListAdapter(numberOfGrades)

        val gradesAdapter = GradesAdapter(this, grades)
        list.adapter = gradesAdapter
        setButtonOnClick()
    }

    private fun fillListAdapter(numberOfElements: Int) {
        for(i in 1..numberOfElements) {
            grades.add(Grade(grade + i.toString(), 2))
        }
    }

    private fun setButtonOnClick() {
        calculateBtn.setOnClickListener {
            var sum = BigDecimal.ZERO
            for (grade in grades) {
                sum = sum.plus(BigDecimal(grade.value))
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra(sumKey, sum.divide(BigDecimal(grades.size)).round(MathContext(2, RoundingMode.HALF_DOWN)).toDouble())
            startActivity(intent)
        }
    }
}