package com.ray.srt_smi_converter.viewmodel

import com.ray.srt_smi_converter.viewmodel.SubtitleHandler.Companion.testReadingAndConverting
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class SubtitleHandlerTest {

    @Before
    fun setUp() {

    }

    @Test
    fun testReadingAndConvertingTester(){
        var testFile = File("::///mnt/sdcard/Movies/Arctic.2018.1080p.BluRay.x264-[YTS.AM].smi")
        var charset = InputStreamReader(FileInputStream(testFile)).encoding

        testReadingAndConverting(testFile, charset)

    }

    @After
    fun tearDown() {

    }
}