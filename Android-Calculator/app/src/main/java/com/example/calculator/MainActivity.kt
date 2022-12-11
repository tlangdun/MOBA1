package com.example.calculator

import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var displayVal: TextView
    private var currNumber = StringBuilder()
    private lateinit var numberButtons: Array<Button>
    private lateinit var operatorButtons: Array<Button>
    private var operator: Operator = Operator.NONE
    private var isOperatorClicked: Boolean = false
    private var operand1: Int = 0
    private var hasOperand1 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayVal = binding.result

        numberButtons = arrayOf(
            binding.number0,
            binding.number01,
            binding.number02,
            binding.number03,
            binding.number04,
            binding.number05,
            binding.number06,
            binding.number07,
            binding.number08,
            binding.number09
        )

        operatorButtons = arrayOf(
            binding.plus,
            binding.minus,
            binding.multiply,
            binding.divide
        )

        for (i in numberButtons) {
            i.setOnClickListener { handleNumberButtonClick(i) }
        }

        for (i in operatorButtons) {
            i.setOnClickListener { handleOperationButtonClick(i) }
        }

        binding.equals.setOnClickListener { buttonEqualClick() }
        binding.clear.setOnClickListener { buttonClearClick() }
    }

    private fun buttonClearClick() {
        currNumber.clear()
        operand1 = 0
        operator = Operator.NONE
        isOperatorClicked = false
        hasOperand1 = false
        displayVal.text = ""
    }

    private fun buttonEqualClick() {
        val operand2 = currNumber.toString().toInt()
        try {
            val result = when(operator) {
                Operator.ADD -> operand1 + operand2
                Operator.SUB -> operand1 - operand2
                Operator.MUL -> operand1 * operand2
                Operator.DIV -> operand1 / operand2
                else -> 0
            }

            currNumber.clear()
            currNumber.append(result.toString())
            updateDisplay()
            isOperatorClicked = true
            hasOperand1 = false
        } catch (e: ArithmeticException) {
            buttonClearClick()
            displayVal.text = "Cannot be divided by 0"
        }

    }

    private fun updateDisplay() {
        try {
            val textValue = currNumber.toString().toInt()
            displayVal.text = textValue.toString()
        } catch (e: IllegalArgumentException){
            buttonClearClick()
            displayVal.text = "Error"
        }

    }

    private fun handleOperationButtonClick(btn: Button) {
        if (hasOperand1) {
            buttonEqualClick()
        }
        if (btn.text == "+") operator = Operator.ADD
        else if (btn.text == "-") operator = Operator.SUB
        else if (btn.text == "*") operator = Operator.MUL
        else if (btn.text == "/") operator = Operator.DIV
        else operator = Operator.NONE
        isOperatorClicked = true
    }

    private fun handleNumberButtonClick(btn: Button) {
        if (isOperatorClicked) {
            operand1 = currNumber.toString().toInt()
            hasOperand1 = true
            currNumber.clear()
            isOperatorClicked = false
        }

        currNumber.append(btn.text)
        updateDisplay()
    }

}

enum class Operator {ADD, SUB, MUL, DIV, NONE}