package com.example.measuredata;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/example/measuredata/UiState;", "", "()V", "HeartRateAvailable", "HeartRateNotAvailable", "Startup", "Lcom/example/measuredata/UiState$HeartRateAvailable;", "Lcom/example/measuredata/UiState$HeartRateNotAvailable;", "Lcom/example/measuredata/UiState$Startup;", "app_debug"})
public abstract class UiState {
    
    private UiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/measuredata/UiState$Startup;", "Lcom/example/measuredata/UiState;", "()V", "app_debug"})
    public static final class Startup extends com.example.measuredata.UiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.measuredata.UiState.Startup INSTANCE = null;
        
        private Startup() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/measuredata/UiState$HeartRateAvailable;", "Lcom/example/measuredata/UiState;", "()V", "app_debug"})
    public static final class HeartRateAvailable extends com.example.measuredata.UiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.measuredata.UiState.HeartRateAvailable INSTANCE = null;
        
        private HeartRateAvailable() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/measuredata/UiState$HeartRateNotAvailable;", "Lcom/example/measuredata/UiState;", "()V", "app_debug"})
    public static final class HeartRateNotAvailable extends com.example.measuredata.UiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.measuredata.UiState.HeartRateNotAvailable INSTANCE = null;
        
        private HeartRateNotAvailable() {
            super();
        }
    }
}