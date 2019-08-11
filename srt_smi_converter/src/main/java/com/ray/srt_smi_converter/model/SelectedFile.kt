package com.ray.srt_smi_converter.model

import java.io.File

class SelectedFile(val file: File? = null, val name:String ="", val extension:String = ""){
    operator fun invoke(selectedFile: SelectedFile) {
            mFile = selectedFile.mFile
            mFileName = selectedFile.mFileName
            mExtension = selectedFile.mExtension
    }

    private var mFile: File? = file
            get() = field

        private var mFileName:String? = name
            get()=field

        private var mExtension:String = extension
            get()=field

}