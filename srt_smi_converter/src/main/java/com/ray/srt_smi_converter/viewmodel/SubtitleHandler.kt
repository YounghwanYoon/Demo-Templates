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
                tempList = parseSMIData(file)
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

                   } else{
                       tempData.mLinesOfTexts?.add(it)
                   }
                }
                if(tempData.mEndingTime !=0 && tempData.mEndingTime > tempData.mStartingTime){
                    data.add(BasedSubtitleData(tempData.mLinesOfTexts, tempData.mStartingTime, tempData.mEndingTime))
                }
            }
            return data
        }

        fun testReadingAndConverting(file:File, charset:String){
            var listOfArray = mutableListOf<ByteArray> ()
            var listOfText = mutableListOf<String>()
           /* file.forEachLine(Charset.forName(InputStreamReader(FileInputStream(file), charset).encoding)) {
                run {
                    listOfArray.add(it.toByteArray())
                    listOfText.add(it)
                    Log.d(TAG, "Each ByteArray is  ${listOfArray.last()}")
                    Log.d(TAG, "Each File is  ${it}")
                    Log.d(TAG, "Each File is  ${listOfText.last()}")
                }
            }*/
        }
        fun createSRT( selectedFile:File):Boolean{
            val SavingFolder = selectedFile.parentFile
            var createdSRT: Boolean = false
/*
            newSMIFile.appendText("<============================ readLines with charset==============>")
            selectedFile.readLines(charset("MS949")).forEach{
                Log.d(TAG, it)
                newSMIFile.appendText(it + newLine)
            }

            newSMIFile.appendText("<============================ readLines without charset==============>")
            selectedFile.readLines().forEach{
                Log.d(TAG, it)
                newSMIFile.appendText(it + newLine)
            }
            newSMIFile.appendText(newLine + "<============================ ReadText==============>")

            selectedFile.readLines(charset("MS949")).forEach{
                Log.d(TAG, it.toByteArray().toString())
                newSMIFile.appendText(it.toByteArray().toString() + newLine)
            }
*/
            /*
  //Copying File
             var CopyFile = File(SavingFolder,"Copy_${selectedFile.nameWithoutExtension}.txt")
             if(CopyFile.exists()){
                 CopyFile.delete()
             }
             CopyFile.createNewFile()
             CopyFile = selectedFile.copyTo(CopyFile, true)
 */
            val extension = selectedFile.extension
            Log.d(TAG, "Extension of File is $extension")
            when(extension){
                "smi" -> createdSRT= sortSMI(SavingFolder, selectedFile).createNewFile()
                "srt" -> createdSRT = sortSRT(SavingFolder, selectedFile).createNewFile()
            }
            return createdSRT
        }

        private fun sortSMI(SavingTo:File, file:File): File{
            Log.d(TAG,"sortSMI()")
            Log.d(TAG, "default charset is ${Charset.defaultCharset()}")
            var isAfterFirstSync = false
            val regRemoveTag = Regex("<.*?>")
            val tempFile = File(SavingTo, "Temp_${file.nameWithoutExtension}.txt" )
            val newLine = "\n\r"
            val endingTimeArray = mutableListOf<String>()

            // First Sorting
            //Charset.defaultCharset()
            file.readLines(charset("MS-949")).forEach{
                Log.d(TAG, "Each Line ${it}")
                if(it.contains("<SYNC Start=") && !(it.contains("nbsp")|| it.contains("NBSP"))){
                    tempFile.appendText(millisecToReadableTime(it.substringAfter("Start=").substringBefore("><P").toInt()) +" --> " + newLine)
                    isAfterFirstSync = true
                }

                if(isAfterFirstSync && ! (it.contains("SYNC") ||it.contains("sync"))){
                    tempFile.appendText(it.replace(regRemoveTag, "") + newLine)
                    //tempFile.appendText(String(it.replace(regRemoveTag, "").toByteArray(Charsets.UTF_8)) + newLine)
                }
                if(it.contains("<SYNC Start=") && it.contains("nbsp")||it.contains("NBSP")){
                    endingTimeArray.add(millisecToReadableTime(it.substringAfter("Start=").substringBefore("><P").toInt()).toString() + newLine)
                }
            }

            var index = 0
            val sortedFile = File(SavingTo, "Converted_${file.nameWithoutExtension}.srt" )
            if(sortedFile.exists()){
                sortedFile.delete()
            }
            //sortedFile.createNewFile()

            //Second Sorting
            //Charset.defaultCharset()
            tempFile.readLines(/*charset("MS-949")*/).forEach{
                if(it.contains(":") && it.contains(",") && index < endingTimeArray.size){
                    sortedFile.appendText(index.toString())
                    sortedFile.appendText(newLine + System.lineSeparator())
                    sortedFile.appendText( it +endingTimeArray[index] )
                    index++
                } else  {
                    sortedFile.appendText(it + newLine)
                }
            }
            // Log.d(TAG, "Type of Encoder is ${InputStreamReader(FileInputStream(selectedFile)).encoding}")
            //Log.d(TAG, "tempFile encoding is ${InputStreamReader(FileInputStream(tempFile)).encoding}")
            //Log.d(TAG, "sortedFile encoding is ${InputStreamReader(FileInputStream(sortedFile)).encoding}")

            if(tempFile.exists()){
                tempFile.delete()
            }

            return sortedFile
        }
        private fun sortSRT(SavingTo:File, file:File):File{
            val tempFile:File
            val sortedFile: File = File.createTempFile("TempSRT", ".txt")

            return sortedFile
        }


        fun millisecToReadableTime(milliseconds:Int):String{
            val remainMilisec:Int =(milliseconds%1000)
            val seconds:Int = ((milliseconds/1000) %60)
            val minutes:Int= ((milliseconds/(1000*60))%60)
            val hours:Int = ((milliseconds/(1000*60*60))%24)

            val hoursStr = if (hours < 10) "0$hours" else hours.toString()
            val minutesStr = if (minutes < 10) "0$minutes" else minutes.toString()
            val secondsStr = if (seconds < 10) "0$seconds" else seconds.toString()

            val readableTime:String = "$hoursStr:$minutesStr:$secondsStr,$remainMilisec"
            return readableTime
        }
    }
}