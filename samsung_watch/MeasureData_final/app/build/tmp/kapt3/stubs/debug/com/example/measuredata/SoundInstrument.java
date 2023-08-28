package com.example.measuredata;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u000fJ\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u0018H\u0007J\u0006\u0010\u001a\u001a\u00020\u0018R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\b\u00a8\u0006\u001b"}, d2 = {"Lcom/example/measuredata/SoundInstrument;", "", "()V", "audioFormat", "", "getAudioFormat", "()I", "setAudioFormat", "(I)V", "audioRecord", "Landroid/media/AudioRecord;", "channelConfig", "getChannelConfig", "setChannelConfig", "isStarted", "", "minBufferSize", "sampleRateInHz", "getSampleRateInHz", "setSampleRateInHz", "getAmplitude", "", "getIsStarted", "restart", "", "start", "stop", "app_debug"})
public final class SoundInstrument {
    private android.media.AudioRecord audioRecord;
    private int sampleRateInHz = 8000;
    private int channelConfig = android.media.AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = android.media.AudioFormat.ENCODING_PCM_16BIT;
    private int minBufferSize = 0;
    private boolean isStarted = false;
    
    public SoundInstrument() {
        super();
    }
    
    public final int getSampleRateInHz() {
        return 0;
    }
    
    public final void setSampleRateInHz(int p0) {
    }
    
    public final int getChannelConfig() {
        return 0;
    }
    
    public final void setChannelConfig(int p0) {
    }
    
    public final int getAudioFormat() {
        return 0;
    }
    
    public final void setAudioFormat(int p0) {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    public final void start() {
    }
    
    public final void stop() {
    }
    
    public final void restart() {
    }
    
    public final boolean getIsStarted() {
        return false;
    }
    
    public final float getAmplitude() {
        return 0.0F;
    }
}