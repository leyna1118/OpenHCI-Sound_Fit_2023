package com.example.measuredata;

import java.lang.System;

/**
 * Entry point for [HealthServicesClient] APIs, wrapping them in coroutine-friendly APIs.
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0007R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\r"}, d2 = {"Lcom/example/measuredata/HealthServicesManager;", "", "healthServicesClient", "Landroidx/health/services/client/HealthServicesClient;", "(Landroidx/health/services/client/HealthServicesClient;)V", "measureClient", "Landroidx/health/services/client/MeasureClient;", "hasHeartRateCapability", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "heartRateMeasureFlow", "Lkotlinx/coroutines/flow/Flow;", "Lcom/example/measuredata/MeasureMessage;", "app_debug"})
public final class HealthServicesManager {
    private final androidx.health.services.client.MeasureClient measureClient = null;
    
    @javax.inject.Inject()
    public HealthServicesManager(@org.jetbrains.annotations.NotNull()
    androidx.health.services.client.HealthServicesClient healthServicesClient) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasHeartRateCapability(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> continuation) {
        return null;
    }
    
    /**
     * Returns a cold flow. When activated, the flow will register a callback for heart rate data
     * and start to emit messages. When the consuming coroutine is cancelled, the measure callback
     * is unregistered.
     *
     * [callbackFlow] is used to bridge between a callback-based API and Kotlin flows.
     */
    @org.jetbrains.annotations.NotNull()
    @kotlinx.coroutines.ExperimentalCoroutinesApi()
    public final kotlinx.coroutines.flow.Flow<com.example.measuredata.MeasureMessage> heartRateMeasureFlow() {
        return null;
    }
}