package com.nextgentele.busvalidatorv2.roomdb.daointerface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface SjtTicketListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addTickets(List<ModelListActiveSjtTicketPayload> ticketList);

    @Query("SELECT * FROM listSjtTickets WHERE sjtQrcode = :sjtQr")
    Maybe<ModelListActiveSjtTicketPayload> getSjtTicket(String sjtQr);

    @Query("SELECT * FROM listSjtTickets WHERE rjtQrcode = :rjtQr")
    Maybe<ModelListActiveSjtTicketPayload> getRjtTicket(String rjtQr);

    @Update
    Single<Integer> updateTickets(ModelListActiveSjtTicketPayload ticket);
}
