package com.nextgentele.busvalidatorv2.roomdb.daointerface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nextgentele.busvalidatorv2.models.ModelListDepotResponsePayload;
import com.nextgentele.busvalidatorv2.models.Stop;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface StopsDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    Completable addStopsList(List<Stop> list);

    @Query("SELECT * FROM stops ORDER BY stopOrder ASC")
    Flowable<List<Stop>> getStopsList();

    @Query("SELECT stopOrder FROM stops WHERE textualIdentifier = :stopName")
    Single<Integer> getStoporder(String stopName);

    @Query("SELECT backendKey FROM stops WHERE stopOrder = :stopOrder")
    Single<String> getStopKey(int stopOrder);


}
