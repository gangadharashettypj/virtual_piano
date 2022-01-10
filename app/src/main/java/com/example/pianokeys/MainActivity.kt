package com.example.pianokeys

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    var whiteKeyWidth = 40
    var blackKeyWidth = 30
    var whiteKeyHeight = 120
    var blackKeyHeight = 100
    var visibleKeys = 36

    val keys = mutableListOf(
        PianoKeyModel(
            "C",
            "white",
            "",
            "c"
        ),
        PianoKeyModel(
            "C#",
            "black",
            "",
            "c#"
        ),
        PianoKeyModel(
            "D",
            "white",
            "",
            "d"
        ),
        PianoKeyModel(
            "D#",
            "black",
            "",
            "d#"
        ),
        PianoKeyModel(
            "E",
            "white",
            "",
            "e"
        ),
        PianoKeyModel(
            "F",
            "white",
            "",
            "f"
        ),
        PianoKeyModel(
            "F#",
            "black",
            "",
            "f#"
        ),
        PianoKeyModel(
            "G",
            "white",
            "",
            "g"
        ),
        PianoKeyModel(
            "G#",
            "black",
            "",
            "g#"
        ),
        PianoKeyModel(
            "A",
            "white",
            "",
            "a"
        ),
        PianoKeyModel(
            "A#",
            "black",
            "",
            "a#"
        ),
        PianoKeyModel(
            "B",
            "white",
            "",
            "b"
        ),
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pianoView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        pianoView.layoutManager = layoutManager
        pianoView.adapter = PianoViewAdapter(keys, this)
        pianoView.computeHorizontalScrollExtent()
    }
}