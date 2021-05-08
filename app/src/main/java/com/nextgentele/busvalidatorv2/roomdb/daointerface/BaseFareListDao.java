package com.nextgentele.busvalidatorv2.roomdb.daointerface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.nextgentele.busvalidatorv2.models.ModelListDepotResponsePayload;
import com.nextgentele.busvalidatorv2.roomdb.tables.BaseFareList;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface BaseFareListDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    Completable addBaseFareList(List<BaseFareList> list);


}
