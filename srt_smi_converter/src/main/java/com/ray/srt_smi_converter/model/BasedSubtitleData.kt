package com.ray.srt_smi_converter.model

class BasedSubtitleData(){
    companion object{
        var trackingNumber = 0
    }
    constructor(mBasedSubtitleData: BasedSubtitleData):this(){
        mLinesOfTexts = mBasedSubtitleData.mLinesOfTexts
        mStartingTime = mBasedSubtitleData.mStartingTime
        mEndingTime = mBasedSubtitleData.mEndingTime
    }

    constructor(LinesOfTexts: MutableList<String>?, StartingTime: Int, EndingTime:Int ) : this(){
        mLinesOfTexts = LinesOfTexts
        mStartingTime = StartingTime
        mEndingTime = EndingTime
        trackingNumber++
    }
    var mDataNumber: Int = trackingNumber
        get() = field
        set(value) {
            field = value
        }

    var mLinesOfTexts: MutableList<String>? = null
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
}