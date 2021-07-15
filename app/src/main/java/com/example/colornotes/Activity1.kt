package com.example.colornotes

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Activity1 : AppCompatActivity() {

    var colors = arrayOf(0xFFFFFFA0, 0xFFFFA0FF, 0xFFA0FFFF)
    var sheetNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        sheetNumber = intent.getIntExtra("sheetNumber", 0)
        val nextButton = findViewById<Button>(R.id.next)
        nextButton.setOnClickListener {
            if (sheetNumber < colors.size - 1) {
                val intent = Intent(this, this::class.java) //intent-намерение
                intent.putExtra("sheetNumber", sheetNumber + 1)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Last page", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        //передаем переменной prefs хранилище и .edit() - открываем на запись
        val prefs = getPreferences(Context.MODE_PRIVATE).edit()
        val text = findViewById<EditText>(R.id.text1)
        //получаем значение заметки; название ключа - note1
        prefs.putString("note" + sheetNumber.toString(), text.editableText.toString())
        //применить изменения
        prefs.apply()
    }

    override fun onResume() {
        super.onResume()
        //установить цвет листа
        val sheet = findViewById<ConstraintLayout>(R.id.sheet)
        sheet.setBackgroundColor(colors[sheetNumber].toInt())
        //созд перем, получим доступ к заметке; значение по умолчанию - "" - пустая строка
        val savedText = getPreferences(Context.MODE_PRIVATE).getString("note" + sheetNumber.toString(), null)
        val text = findViewById<EditText>(R.id.text1)
        //если заметка не пустая, установим в поле text ее значение
        if (savedText != null) {
            text.setText(savedText)
        }

    }

}