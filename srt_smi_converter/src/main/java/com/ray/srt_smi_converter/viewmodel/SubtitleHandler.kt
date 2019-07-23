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
                    //Log.d(TAG, "mStartingTime is: ${tempData.mStartingTime}")
                    firstSYNPassed= true
                }
                if(it.contains("<SYNC Start=") && it.contains("nbsp")||it.contains("NBSP")){
                    tempData.mEndingTime = it.substringAfter("Start=").substringBefore("><P").toInt()
                    //Log.d(TAG, "mEndingTime is: ${tempData.mEndingTime}")
                }
                if(firstSYNPassed && !it.contains("SYNC")){
                    //Remove Tag
                   if(it.contains("<") || it.contains(">")) {
                       tempData.mLinesOfTexts?.add(
                               String(it
                                       .replace(reg,"")
                                       .toByteArray(Charset.forName("MS949"))))

                       //Log.d(TAG, "mLinesOfTexts is: ${ it.replace(reg, "")}")
                      // Log.d(TAG, "tempData.mLinesOfTexts is: ${tempData.mLinesOfTexts?.get(0)?.toString()}")

                   } else tempData.mLinesOfTexts?.add(it)
                }
                if(tempData.mEndingTime !=0 && tempData.mEndingTime > tempData.mStartingTime){
                    data.add(BasedSubtitleData(tempData.mLinesOfTexts, tempData.mStartingTime, tempData.mEndingTime))
                }
            }
            return data
        }
        fun createSRT(contents:MutableList<BasedSubtitleData>, selectedFile:File,appContext:Context){
            //Log.d(TAG, "size of contents is ${contents.size}")

            var tempText:String = ""
            val SavingFolder = selectedFile.parentFile
            var newSMIFile = File(SavingFolder, selectedFile.nameWithoutExtension +".txt")

/*
            TesterSMIFile.appendText("\n\r =================using readLines==========\n\r ")
            selectedFile.readLines(charset("MS949")).forEach {
                TesterSMIFile.appendText(it + "\n\r")
            }

            TesterSMIFile.readLines().forEach {
                Log.d(TAG, "TesterSMIFile is: ${it.toString()}")
            }
*/
            //Check whether file with the path name exists or not. If so, delete it before creating a new file.
            if(newSMIFile.exists()){
                Log.d(TAG, "Directory exist!")
                newSMIFile.delete()
            }
            Log.d(TAG, "Inside Try!!")
            newSMIFile.createNewFile()

            var i = 0
            /*
            selectedFile.readLines(charset("MS949")).forEach{
            //contents.forEach{
                //Adding Index
                newSMIFile.appendText(String("$i \n\r".toByteArray(), Charset.forName("MS949")))

                //Adding Time Frame
                newSMIFile.appendText(
                        String("${millisecToReadableTime(it.mStartingTime)}-->${millisecToReadableTime(it.mEndingTime)}"
                                .toByteArray(Charset.forName("MS949"))))
                newSMIFile.appendText("\n\r")

                //Adding Subtitle/Text
                if(it.mLinesOfTexts != null){
                    it.mLinesOfTexts!!.forEach {
                        tempText = String((tempText + it + "\n").toByteArray(Charset.forName("MS949")))
                    }
                    newSMIFile.appendText((tempText ))
                    newSMIFile.appendText("\n\r")
                }else{
                    newSMIFile.appendText("\n\r")
                }
                tempText = ""
                i++
            }
*/
            var firstSYNPassed = false
            var tempData:BasedSubtitleData = BasedSubtitleData()
            val reg = Regex("<.*?>")
            selectedFile.readLines(charset("MS949")).forEach {

                if(it.contains("<SYNC Start=") && !(it.contains("nbsp") || it.contains("NBSP"))) {
                    tempData.mStartingTime = it.substringAfter("Start=").substringBefore("><P").toInt()
                    //Log.d(TAG, "mStartingTime is: ${tempData.mStartingTime}")
                    firstSYNPassed = true
                }

                if (firstSYNPassed && !it.contains("SYNC")) {
                    //Remove Tag
                    if (it.contains("<") || it.contains(">")) {
                        tempData.mLinesOfTexts?.add(
                                /*String(*/it
                                        .replace(reg, "")
                                        /*.toByteArray(Charset.forName("MS949")))*/)
                    }
                }
                if (it.contains("<SYNC Start=") && it.contains("nbsp") || it.contains("NBSP")) {
                    tempData.mEndingTime = it.substringAfter("Start=").substringBefore("><P").toInt()
                   // Log.d(TAG, "mEndingTime is: ${tempData.mEndingTime}")
                }
                //Once it is reached Ending Time, all the texts and time frame should be ready to store
                if(tempData.mEndingTime !=0 && tempData.mEndingTime > tempData.mStartingTime){
                    //Adding Index
                    newSMIFile.appendText(/*String(*/"$i \n\r"/*.toByteArray(charset("MS949")))*/)
                    //Adding Time Frame
                    newSMIFile.appendText(
                            /*String(*/"${millisecToReadableTime(tempData.mStartingTime)}-->${millisecToReadableTime(tempData.mEndingTime)}"
                                    /*.toByteArray(Charset.forName("MS949")))*/)
                    newSMIFile.appendText(/*String(*/"\n\r"/*.toByteArray(charset("MS949")))*/)
                    tempData.mLinesOfTexts?.forEach {
                        newSMIFile.appendText(/*String(*/it/*.toByteArray(charset("MS949")))*/)
                        newSMIFile.appendText(/*String(*/"\n\r"/*.toByteArray(charset("MS949")))*/)                    }
                    i++
                }

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