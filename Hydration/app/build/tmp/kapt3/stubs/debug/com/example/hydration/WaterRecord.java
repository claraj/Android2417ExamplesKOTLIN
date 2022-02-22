package com.example.hydration;

import java.lang.System;

@androidx.room.Entity()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0007\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0017\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u0003H\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2 = {"Lcom/example/hydration/WaterRecord;", "", "day", "", "glasses", "", "(Ljava/lang/String;I)V", "getDay", "()Ljava/lang/String;", "value", "getGlasses", "()I", "setGlasses", "(I)V", "toString", "Companion", "app_debug"})
public final class WaterRecord {
    @org.jetbrains.annotations.NotNull()
    @androidx.room.PrimaryKey()
    private final java.lang.String day = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.hydration.WaterRecord.Companion Companion = null;
    public static final int MAX_GLASSES = 3;
    private int glasses;
    
    public WaterRecord(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    java.lang.String day, int glasses) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDay() {
        return null;
    }
    
    public final int getGlasses() {
        return 0;
    }
    
    public final void setGlasses(int value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/hydration/WaterRecord$Companion;", "", "()V", "MAX_GLASSES", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}