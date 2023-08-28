package com.example.measuredata;

import java.lang.System;

/**
 * Holds most of the interaction logic and UI state for the app.
 */
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0014\u001a\u00020\u0015H\u0087@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/example/measuredata/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "healthServicesManager", "Lcom/example/measuredata/HealthServicesManager;", "(Lcom/example/measuredata/HealthServicesManager;)V", "_heartRateAvailable", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Landroidx/health/services/client/data/DataTypeAvailability;", "_heartRateBpm", "", "_uiState", "Lcom/example/measuredata/UiState;", "heartRateAvailable", "Lkotlinx/coroutines/flow/StateFlow;", "getHeartRateAvailable", "()Lkotlinx/coroutines/flow/StateFlow;", "heartRateBpm", "getHeartRateBpm", "uiState", "getUiState", "measureHeartRate", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.measuredata.HealthServicesManager healthServicesManager = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.measuredata.UiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.measuredata.UiState> uiState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<androidx.health.services.client.data.DataTypeAvailability> _heartRateAvailable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<androidx.health.services.client.data.DataTypeAvailability> heartRateAvailable = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Double> _heartRateBpm = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Double> heartRateBpm = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.example.measuredata.HealthServicesManager healthServicesManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.measuredata.UiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<androidx.health.services.client.data.DataTypeAvailability> getHeartRateAvailable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Double> getHeartRateBpm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @kotlinx.coroutines.ExperimentalCoroutinesApi()
    public final java.lang.Object measureHeartRate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}