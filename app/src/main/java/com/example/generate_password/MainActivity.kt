package com.example.generate_password

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var passwordTextView: TextView
    private lateinit var generateButton: Button
    private lateinit var copyButton: Button
    private lateinit var includeUpperCase: CheckBox
    private lateinit var includeLowerCase: CheckBox
    private lateinit var includeNumbers: CheckBox
    private lateinit var excludeSimilar: CheckBox
    private lateinit var passwordLengthSeekBar: SeekBar
    private lateinit var seekBarValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        passwordTextView = findViewById(R.id.passwordTextView)
        generateButton = findViewById(R.id.generateButton)
        copyButton = findViewById(R.id.copyButton)
        includeUpperCase = findViewById(R.id.includeUpperCase)
        includeLowerCase = findViewById(R.id.includeLowerCase)
        includeNumbers = findViewById(R.id.includeNumbers)
        excludeSimilar = findViewById(R.id.excludeSimilar)
        passwordLengthSeekBar = findViewById(R.id.passwordLengthSeekBar)
        seekBarValue = findViewById(R.id.seekBarValue)

        passwordLengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        generateButton.setOnClickListener { generatePassword() }
        copyButton.setOnClickListener { copyPasswordToClipboard() }
    }

    private fun generatePassword() {
        val length = passwordLengthSeekBar.progress
        val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerCase = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val similarCharacters = "il1Lo0O"
        var charPool = ""
        val password = StringBuilder()

        if (includeUpperCase.isChecked) {
            charPool += upperCase
            password.append(upperCase.random())
        }
        if (includeLowerCase.isChecked) {
            charPool += lowerCase
            password.append(lowerCase.random())
        }
        if (includeNumbers.isChecked) {
            charPool += numbers
            password.append(numbers.random())
        }

        if (excludeSimilar.isChecked) {
            charPool = charPool.filterNot { it in similarCharacters }
        }

        if (charPool.isEmpty()) {
            Toast.makeText(this, "Select at least one character type", Toast.LENGTH_SHORT).show()
            return
        }

        while (password.length < length) {
            password.append(charPool.random())
        }

        // Shuffle to ensure randomness
        val shuffledPassword = password.toList().shuffled().joinToString("")

        passwordTextView.text = shuffledPassword
        passwordTextView.setTextColor(resources.getColor(android.R.color.black, null))
    }

    private fun copyPasswordToClipboard() {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("Generated Password", passwordTextView.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}
