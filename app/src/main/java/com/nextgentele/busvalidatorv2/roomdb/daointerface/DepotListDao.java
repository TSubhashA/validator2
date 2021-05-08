package com.nextgentele.busvalidatorv2.roomdb.daointerface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nextgentele.busvalidatorv2.models.ModelListDepotResponsePayload;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;


@Dao
public interface DepotListDao {

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    Completable addDepotList(List<ModelListDepotResponsePayload> list);

    @Query("SELECT numericIdentifier FROM depotList")
    Maybe<List<String>> getDepotList();

    @Query("SELECT backendKey FROM depotList WHERE numericIdentifier = :numericId LIMIT 1")
    Single<String> getDepotBackend(String numericId);
}
