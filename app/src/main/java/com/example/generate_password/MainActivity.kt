package com.example.generate_password

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var generatedPasswordTextView: TextView
    private lateinit var passwordLengthSeekBar: SeekBar
    private lateinit var useLettersSwitch: Switch
    private lateinit var useNumbersSwitch: Switch
    private lateinit var useCapitalSwitch: Switch
    private lateinit var useSpecialCharsSwitch: Switch
    private lateinit var generateButton: Button
    private lateinit var copyButton: Button
    private var passwordLength = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializando os componentes
        generatedPasswordTextView = findViewById(R.id.tv_generated_password)
        passwordLengthSeekBar = findViewById(R.id.seekBar_password_length)
        useLettersSwitch = findViewById(R.id.switch_use_letters)
        useNumbersSwitch = findViewById(R.id.switch_use_numbers)
        useCapitalSwitch = findViewById(R.id.switch_use_capital)
        useSpecialCharsSwitch = findViewById(R.id.switch_use_special_chars)
        generateButton = findViewById(R.id.btn_generate)
        copyButton = findViewById(R.id.btn_copy)

        val textView = findViewById<TextView>(R.id.tv_password_length_value)

        // Inicialize o valor do TextView com o valor atual da SeekBar
        textView.text = passwordLengthSeekBar.progress.toString()

        // Unifica o listener da SeekBar para atualizar o TextView e a variável passwordLength
        passwordLengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Atualize o TextView com o valor atual da SeekBar
                textView.text = progress.toString()
                // Atualize o comprimento da senha
                passwordLength = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Não é necessário usar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Não é necessário usar
            }
        })

        // Gerar senha quando o botão for pressionado
        generateButton.setOnClickListener {
            val password = generatePassword()
            generatedPasswordTextView.text = password
        }

        // Copiar senha para a área de transferência
        copyButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Password", generatedPasswordTextView.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }


    private fun generatePassword(): String {
        val letters = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val specialChars = "!@#\$%^&*()-_=+[]{}"

        var charPool = ""

        if (useLettersSwitch.isChecked) charPool += letters
        if (useNumbersSwitch.isChecked) charPool += numbers
        if (useCapitalSwitch.isChecked) charPool += capitals
        if (useSpecialCharsSwitch.isChecked) charPool += specialChars

        return (1..passwordLength)
            .map { charPool[Random.nextInt(0, charPool.length)] }
            .joinToString("")
    }


}