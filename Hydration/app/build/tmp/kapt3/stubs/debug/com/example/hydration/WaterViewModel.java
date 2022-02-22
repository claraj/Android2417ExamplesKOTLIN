package com.example.hydration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0014J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\tJ\u000e\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nJ\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nR\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/hydration/WaterViewModel;", "Landroidx/lifecycle/ViewModel;", "waterRepository", "Lcom/example/hydration/WaterRepository;", "daysRepository", "Lcom/example/hydration/DaysRepository;", "(Lcom/example/hydration/WaterRepository;Lcom/example/hydration/DaysRepository;)V", "allRecords", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/hydration/WaterRecord;", "getAllRecords", "()Landroidx/lifecycle/LiveData;", "deleteRecord", "", "record", "getRecordForDay", "day", "", "getTodayIndex", "", "getWeekdays", "insertNewRecord", "updateRecord", "app_debug"})
public final class WaterViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.hydration.WaterRepository waterRepository = null;
    private final com.example.hydration.DaysRepository daysRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.example.hydration.WaterRecord>> allRecords = null;
    
    public WaterViewModel(@org.jetbrains.annotations.NotNull()
    com.example.hydration.WaterRepository waterRepository, @org.jetbrains.annotations.NotNull()
    com.example.hydration.DaysRepository daysRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.hydration.WaterRecord>> getAllRecords() {
        return null;
    }
    
    public final void insertNewRecord(@org.jetbrains.annotations.NotNull()
    com.example.hydration.WaterRecord record) {
    }
    
    public final void updateRecord(@org.jetbrains.annotations.NotNull()
    com.example.hydration.WaterRecord record) {
    }
    
    public final void deleteRecord(@org.jetbrains.annotations.NotNull()
    com.example.hydration.WaterRecord record) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.hydration.WaterRecord> getRecordForDay(@org.jetbrains.annotations.NotNull()
    java.lang.String day) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getWeekdays() {
        return null;
    }
    
    public final int getTodayIndex() {
        return 0;
    }
}