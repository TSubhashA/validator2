package com.nextgentele.busvalidatorv2.roomdb.daointerface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelExitSjtTicket;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface ExitSjtDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    Completable addExit(ModelExitSjtTicket ticket);

    @Query("SELECT * FROM EXITSJTTICKET LIMIT 1 ")
    Maybe<ModelExitSjtTicket> getExit();

    @Delete
    Completable deleteExit(ModelExitSjtTicket modelExitSjtTicket);
}
