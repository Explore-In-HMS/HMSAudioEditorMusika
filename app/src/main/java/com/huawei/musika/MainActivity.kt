// Copyright 2020. Explore in HMS. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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