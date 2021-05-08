package com.nextgentele.busvalidatorv2.roomdb.daointerface;

import androidx.room.Dao;
import androidx.room.Insert;

import com.nextgentele.busvalidatorv2.roomdb.tables.TripDetails;

import io.reactivex.Completable;


@Dao
public interface TripsDetailsDao {
    @Insert
    Completable addTrips(TripDetails tripDetails);

}
