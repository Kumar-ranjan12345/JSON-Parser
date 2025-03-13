package com.kumar.jsonparser

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kumar.jsonparser.Parser.JsonParser
import om.kumar.jsonparser.Lexer.JsonLexer

class MainActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContentView(R.layout.activity_main)

      parseJSON()
   }

   private fun parseJSON() {
      val json = """{
           "name": "Alice",
           "age": 25,
           "isStudent": false,
           "subjects": ["Math", "Science"],
           "address": { "city": "Bengaluru", "country": "India" }
       }"""

      val lexer = JsonLexer(json)
      val parser = JsonParser(lexer)
      val parsedJson = parser.parse()

      Log.e("ParsedJSON", parsedJson.toString())
   }

}
