package com.example.hydration;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WaterDao_Impl implements WaterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WaterRecord> __insertionAdapterOfWaterRecord;

  private final EntityDeletionOrUpdateAdapter<WaterRecord> __deletionAdapterOfWaterRecord;

  private final EntityDeletionOrUpdateAdapter<WaterRecord> __updateAdapterOfWaterRecord;

  public WaterDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWaterRecord = new EntityInsertionAdapter<WaterRecord>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `WaterRecord` (`day`,`glasses`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WaterRecord value) {
        if (value.getDay() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getDay());
        }
        stmt.bindLong(2, value.getGlasses());
      }
    };
    this.__deletionAdapterOfWaterRecord = new EntityDeletionOrUpdateAdapter<WaterRecord>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `WaterRecord` WHERE `day` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WaterRecord value) {
        if (value.getDay() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getDay());
        }
      }
    };
    this.__updateAdapterOfWaterRecord = new EntityDeletionOrUpdateAdapter<WaterRecord>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `WaterRecord` SET `day` = ?,`glasses` = ? WHERE `day` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WaterRecord value) {
        if (value.getDay() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getDay());
        }
        stmt.bindLong(2, value.getGlasses());
        if (value.getDay() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDay());
        }
      }
    };
  }

  @Override
  public Object insert(final WaterRecord record, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWaterRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object delete(final WaterRecord record, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWaterRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object update(final WaterRecord record, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWaterRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<WaterRecord> getRecordForDay(final String day) {
    final String _sql = "SELECT * FROM WaterRecord WHERE day = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (day == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, day);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"WaterRecord"}, new Callable<WaterRecord>() {
      @Override
      public WaterRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "day");
          final int _cursorIndexOfGlasses = CursorUtil.getColumnIndexOrThrow(_cursor, "glasses");
          final WaterRecord _result;
          if(_cursor.moveToFirst()) {
            final String _tmpDay;
            if (_cursor.isNull(_cursorIndexOfDay)) {
              _tmpDay = null;
            } else {
              _tmpDay = _cursor.getString(_cursorIndexOfDay);
            }
            final int _tmpGlasses;
            _tmpGlasses = _cursor.getInt(_cursorIndexOfGlasses);
            _result = new WaterRecord(_tmpDay,_tmpGlasses);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<WaterRecord>> getAllRecords() {
    final String _sql = "SELECT * FROM WaterRecord";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"WaterRecord"}, new Callable<List<WaterRecord>>() {
      @Override
      public List<WaterRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "day");
          final int _cursorIndexOfGlasses = CursorUtil.getColumnIndexOrThrow(_cursor, "glasses");
          final List<WaterRecord> _result = new ArrayList<WaterRecord>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WaterRecord _item;
            final String _tmpDay;
            if (_cursor.isNull(_cursorIndexOfDay)) {
              _tmpDay = null;
            } else {
              _tmpDay = _cursor.getString(_cursorIndexOfDay);
            }
            final int _tmpGlasses;
            _tmpGlasses = _cursor.getInt(_cursorIndexOfGlasses);
            _item = new WaterRecord(_tmpDay,_tmpGlasses);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
