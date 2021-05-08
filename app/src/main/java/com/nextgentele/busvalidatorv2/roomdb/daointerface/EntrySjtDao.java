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
public interface EntrySjtDao {

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    Completable addEntry(ModelEntrySjtTicket ticket);

    @Query("SELECT * FROM ENTRYSJTTICKET LIMIT 1 ")
    Maybe<ModelEntrySjtTicket> getEntry();

    @Delete
    Completable deleteEntry(ModelEntrySjtTicket modelExitSjtTicket);

}
