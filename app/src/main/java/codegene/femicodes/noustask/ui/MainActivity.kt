package codegene.femicodes.noustask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import codegene.femicodes.noustask.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

}