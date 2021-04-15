package com.example.kotlintts

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.util.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var editText:EditText?=null
    private var buttonSpeak:Button?= null
    private var tts:TextToSpeech?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = this.findViewById(R.id.button_speak)
        editText=this.findViewById(R.id.tts_input)
        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this,this)

        buttonSpeak!!.setOnClickListener{
            readText()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.FRENCH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    private fun readText(){
        val text = editText!!.text.toString();
        Log.d("Text", text)
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


}