package com.ray.srt_smi_converter.model

class BasedSubtitleData(){
    constructor(LineNumber:Int, ListOfLines: MutableList<String>, StartingTime: Int, EndingTime:Int ) : this(){
        mListOfLines = ListOfLines
        mStartingTime = StartingTime
        mEndingTime = EndingTime
    }

    companion object{
        private var mTotalLines: Int = 0
        fun counting(){
            mTotalLines += 1
        }
    }
    var mCurrentLine:Int =0
        get() =field
        set(value){
            field=value
        }
    var mListOfLines: MutableList<String>? = null
        get() = field
        set(value) {
            field = value
        }
    var mStartingTime:Int = 0
        get() = field
        set(value) {
            field = value
        }
    var mEndingTime:Int= 0
        get() = field
        set(value) {
            field = value
        }
    init{
        counting()
        mCurrentLine = mTotalLines
    }

}