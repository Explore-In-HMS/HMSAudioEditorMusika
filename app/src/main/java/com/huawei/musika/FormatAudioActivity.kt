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

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.audioeditor.sdk.HAEAudioExpansion
import com.huawei.hms.audioeditor.sdk.OnTransformCallBack
import java.io.File

class FormatAudioActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var btnSelectAudio: Button? =  null
    private var btnConvertAudio:android.widget.Button? = null
    private var txtSourceFilePath: TextView? =  null
    private var txtDestFilePath:TextView? = null
    private var txtProgress:TextView? = null
    private var spinner: Spinner? = null
    private var edxTxtFileName: EditText? = null
    private val fileType = arrayOf("Select File", "MP3", "WAV", "M4A", "AAC")
    // private val REQUEST_CODE = 101
    private var toConvertFileType: String? = null
    private var progressBar: ProgressBar? = null
    private var sourceFilePath: String? = null
    private var destFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_format_audio)


        btnSelectAudio = findViewById<View>(R.id.select_file) as Button
        btnConvertAudio = findViewById<View>(R.id.format_file) as Button
        txtSourceFilePath = findViewById<View>(R.id.source_file_path) as TextView
        txtProgress = findViewById<View>(R.id.txt_progress) as TextView
        txtDestFilePath = findViewById<View>(R.id.dest_file_path) as TextView
        edxTxtFileName = findViewById<View>(R.id.filename) as EditText
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        spinner = findViewById<View>(R.id.spinner) as Spinner
        spinner!!.onItemSelectedListener = this
        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, fileType)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = adapter
        // Get the source file path
        btnSelectAudio!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "audio/*"
            activityResultLauncher.launch(intent)
        })
        // Convert file to selected format
        btnConvertAudio!!.setOnClickListener {
            createDestFilePath()
            convertFileToSelectedFormat(this@FormatAudioActivity)
        }

    }

    private fun createDestFilePath() {
        val fileName = edxTxtFileName!!.text.toString()
        val file = File(Environment.getExternalStorageDirectory().toString() + "/AudioEdit/FormatAudio")
        if (!file.exists()) {
            file.mkdirs()
        }
        destFilePath = file.absolutePath + File.separator + fileName + "." + toConvertFileType
    }

    @SuppressLint("SetTextI18n")
    private var activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // There are no request codes
            val data = result.data
            if (data!!.data != null) {
                sourceFilePath = Utils.getPathFromUri(this@FormatAudioActivity, data!!.data!!)
                txtSourceFilePath!!.text = "Source File : $sourceFilePath"
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0) {
            toConvertFileType = fileType[position]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun convertFileToSelectedFormat(context: Context) {
        // API for converting the audio format.
        HAEAudioExpansion.getInstance()
            .transformAudio(context, sourceFilePath, destFilePath, object : OnTransformCallBack {
                // Called to receive the progress which ranges from 0 to 100.
                @SuppressLint("SetTextI18n")
                override fun onProgress(progress: Int) {
                    progressBar!!.visibility = View.VISIBLE
                    txtProgress!!.visibility = View.VISIBLE
                    progressBar!!.progress = progress
                    txtProgress!!.text = "$progress/100"
                }
                // Called when the conversion fails.
                override fun onFail(errorCode: Int) {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                }
                // Called when the conversion succeeds.
                @SuppressLint("SetTextI18n")
                override fun onSuccess(outPutPath: String) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    txtDestFilePath!!.text = "Destination Path : $outPutPath"
                }
                // Cancel conversion.
                override fun onCancel() {
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                }
            })
    }

}