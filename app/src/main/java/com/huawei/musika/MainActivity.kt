package com.huawei.musika

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.audioeditor.ui.api.HAEUIManager

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var btnEditAudio: Button? = null
    private var btnConvertAudio:android.widget.Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        // Change the voice.

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEditAudio = findViewById<View>(R.id.edit_audio) as Button
        btnConvertAudio = findViewById<View>(R.id.convert_audio) as Button

        btnEditAudio!!.setOnClickListener(this)
        btnConvertAudio!!.setOnClickListener(this)
        requestPermission()

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.edit_audio -> HAEUIManager.getInstance().launchEditorActivity(this)
            R.id.convert_audio -> {
                val formatAudioIntent = Intent(this, FormatAudioActivity::class.java)
                startActivity(formatAudioIntent)
            }
            else -> {
            }
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO),1001)
        }
    }

}