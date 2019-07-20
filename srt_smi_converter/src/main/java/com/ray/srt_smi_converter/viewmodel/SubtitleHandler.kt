package com.ray.srt_smi_converter.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ray.srt_smi_converter.model.BasedSubtitleData
import java.io.*
import java.nio.charset.Charset

class SubtitleHandler(){
    companion object{
        val TAG = javaClass.simpleName
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext:Context

        fun parseData(file: File, context:Context):MutableList<BasedSubtitleData>{
            Log.d(TAG, "parseData is called")
            val tempList: MutableList<BasedSubtitleData>
            if(file.path.contains(".smi")){
                tempList = parseSMIData(file)
            }
            else{
                tempList = parseSRTData(file)
            }
            return tempList
        }

        fun parseSMIData(file:File):MutableList<BasedSubtitleData>{
            val data: ArrayList<BasedSubtitleData> = ArrayList<BasedSubtitleData>()
            var tempData =  BasedSubtitleData()
            var firstSYNPassed:Boolean = false
            val reg = Regex("<.*?>")
            file.forEachLine{
                if(it.contains("<SYNC Start=") && !(it.contains("nbsp")|| it.contains("NBSP"))){
                    tempData.mStartingTime=it.substringAfter("Start=").substringBefore("><P").toInt()
                    Log.d(TAG, "mStartingTime is: ${tempData.mStartingTime}")
                    firstSYNPassed= true
                }
                if(it.contains("<SYNC Start=") && it.contains("nbsp")||it.contains("NBSP")){
                    tempData.mEndingTime = it.substringAfter("Start=").substringBefore("><P").toInt()
                    Log.d(TAG, "mEndingTime is: ${tempData.mEndingTime}")
                }
                if(firstSYNPassed && !it.contains("SYNC")){
                    //Remove Tag
                   if(it.contains("<") || it.contains(">")) {
                       tempData.mLinesOfTexts?.add(
                               String(it
                                       .replace(reg,"")
                                       .toByteArray(Charset.forName("x-windows-949"))))

                       Log.d(TAG, "mLinesOfTexts is: ${ it.replace(reg, "")}")
                       Log.d(TAG, "tempData.mLinesOfTexts is: ${tempData.mLinesOfTexts?.get(0)?.toString()}")

                   } else tempData.mLinesOfTexts?.add(it)
                }
                if(tempData.mEndingTime !=0 && tempData.mEndingTime > tempData.mStartingTime){
                    data.add(BasedSubtitleData(tempData.mLinesOfTexts, tempData.mStartingTime, tempData.mEndingTime))
                }
            }
            return data
        }
        fun createSRT(contents:MutableList<BasedSubtitleData>, selectedFile:File,appContext:Context){
            Log.d(TAG, "size of contents is ${contents.size}")

            var tempText:String = ""
            val SavingFolder = selectedFile.parentFile
            var newSMIFile = File(SavingFolder, selectedFile.nameWithoutExtension +".srt")
            //Check whether file with the path name exists or not. If so, delete it before creating a new file.
            if(newSMIFile.exists()){
                Log.d(TAG, "Directory exist!")
                newSMIFile.delete()
            }
            Log.d(TAG, "Inside Try!!")
            newSMIFile.createNewFile()

            var i = 0
            contents.forEach{
                //Adding Index
                newSMIFile.appendText(String("$i \n\r".toByteArray(), Charset.forName("EUC-KR")))

                //Adding Time Frame
                newSMIFile.appendText(
                        String("${millisecToReadableTime(it.mStartingTime)}-->${millisecToReadableTime(it.mEndingTime)}"
                                .toByteArray(Charset.forName("EUC-KR"))))
                newSMIFile.appendText("\n\r")

                //Adding Subtitle/Text
                if(it.mLinesOfTexts != null){
                    it.mLinesOfTexts!!.forEach {
                        tempText = String((tempText + it + "\n").toByteArray(Charset.forName("EUC-KR")))
                    }
                    newSMIFile.appendText((tempText ))
                    newSMIFile.appendText("\n\r")
                }else{
                    newSMIFile.appendText("\n\r")
                }
                tempText = ""
                i++
            }

            newSMIFile.forEachLine { Log.d(TAG, "Checking CurrentLine : $it") }
        }
        fun millisecToReadableTime(milliseconds:Int):String{
            var remainMilisec:Int =(milliseconds%1000)
            var seconds:Int = ((milliseconds/1000) %60)
            var minutes:Int= ((milliseconds/(1000*60))%60)
            var hours:Int = ((milliseconds/(1000*60*60))%24)

            var hoursStr = if (hours < 10) "0$hours" else hours.toString()
            var minutesStr = if (minutes < 10) "0$minutes" else minutes.toString()
            var secondsStr = if (seconds < 10) "0$seconds" else seconds.toString()

            var readableTime:String = "$hoursStr:$minutesStr:$secondsStr,$remainMilisec"
            return readableTime
        }
        fun parseSRTData(file:File):MutableList<BasedSubtitleData>{
            val textsFromSMI:MutableList<String>
            val data: MutableList<BasedSubtitleData> = mutableListOf()
            var index:Int = 0
            var tempData = BasedSubtitleData()
            file.forEachLine{
                if(it.contains("<SYNC")){
                    Log.d(TAG, "Inside Contains")
                    tempData.mStartingTime=it.substringAfter("Start=").substringBefore("><P").toInt()
                    Log.d(TAG, "subStrings are: ${it.substringAfter("Start=").substringBefore("><P")}")
                }
            }

            data.add(tempData)
            return data
        }


    }
}