package com.example.measuredata

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class SoundInstrument {
    private lateinit var audioRecord: AudioRecord;

    var sampleRateInHz = 8000;

    var channelConfig = AudioFormat.CHANNEL_IN_MONO;

    var audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private var minBufferSize = 0

    private var isStarted = false;

    @SuppressLint("MissingPermission")
    fun start() {
        minBufferSize = AudioRecord.getMinBufferSize(
            sampleRateInHz,
            channelConfig,
            audioFormat
        )

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRateInHz,
            channelConfig,
            audioFormat,
            minBufferSize
        )

        audioRecord.startRecording()

        isStarted = true
    }

    fun stop() {
        audioRecord.stop()

        isStarted = false
    }

    fun restart() {
        stop()
        start()
    }

    fun getIsStarted(): Boolean {
        return isStarted
    }

//    fun getAmplitude(): Int {
//        if (isStarted == false) {
//            return 0;
//        }
//
//        var buffer = ShortArray(minBufferSize)
//
//        audioRecord.read(buffer, 0, minBufferSize)
//
//        var maxBufferElement = 0
//        for (bufferElement in buffer) {
//            var bufferElementIntegerAbs = Math.abs(bufferElement.toInt())
//
//            if (maxBufferElement < bufferElementIntegerAbs) {
//                maxBufferElement = bufferElementIntegerAbs
//            }
//        }
//
//        return maxBufferElement
//    }

    fun getAmplitude(): Float {
        if (!isStarted) {
            return 0f;
        }

        var buffer = ShortArray(minBufferSize)
        audioRecord.read(buffer, 0, minBufferSize)

        var maxBufferElement = 0
        for (bufferElement in buffer) {
            val bufferElementIntegerAbs = Math.abs(bufferElement.toInt())
            if (maxBufferElement < bufferElementIntegerAbs) {
                maxBufferElement = bufferElementIntegerAbs
            }
        }

//        return maxBufferElement * 100f  // 將 maxBufferElement 值乘以 100
        return maxBufferElement.toFloat()
    }


}