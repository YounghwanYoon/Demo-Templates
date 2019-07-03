package com.ray.srt_smi_converter.model

data class BasedSubtitleData(val ListOfLines: MutableList<String>, val StartingTime: String, val EndingTime:String ){
    private var mListOfLines:MutableList<String> = ListOfLines
        get() = field
        set(value) {
            field = value
        }
    private var mStartingTime:String = StartingTime
        get() = field
        set(value) {
            field = value
        }
    private var mEndingTime:String = EndingTime
        get() = field
        set(value) {
            field = value
        }
}