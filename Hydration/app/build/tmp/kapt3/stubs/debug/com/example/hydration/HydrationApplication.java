package com.example.hydration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0012"}, d2 = {"Lcom/example/hydration/HydrationApplication;", "Landroid/app/Application;", "()V", "database", "Lcom/example/hydration/WaterDatabase;", "getDatabase", "()Lcom/example/hydration/WaterDatabase;", "database$delegate", "Lkotlin/Lazy;", "daysRepository", "Lcom/example/hydration/DaysRepository;", "getDaysRepository", "()Lcom/example/hydration/DaysRepository;", "waterRepository", "Lcom/example/hydration/WaterRepository;", "getWaterRepository", "()Lcom/example/hydration/WaterRepository;", "waterRepository$delegate", "app_debug"})
public final class HydrationApplication extends android.app.Application {
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy waterRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.hydration.DaysRepository daysRepository = null;
    
    public HydrationApplication() {
        super();
    }
    
    private final com.example.hydration.WaterDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.hydration.WaterRepository getWaterRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.hydration.DaysRepository getDaysRepository() {
        return null;
    }
}