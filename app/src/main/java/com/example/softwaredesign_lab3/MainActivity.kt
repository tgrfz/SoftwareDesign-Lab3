package com.example.softwaredesign_lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.softwaredesign_lab3.Data.Note

class MainActivity : AppCompatActivity(), RecordFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onListFragmentInteraction(item: Note?) {
    }


}
