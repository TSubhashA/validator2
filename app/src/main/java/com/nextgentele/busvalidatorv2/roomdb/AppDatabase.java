package com.nextgentele.busvalidatorv2.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelExitSjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.models.ModelListDepotResponsePayload;
import com.nextgentele.busvalidatorv2.models.Stop;
import com.nextgentele.busvalidatorv2.roomdb.daointerface.BaseFareListDao;
import com.nextgentele.busvalidatorv2.roomdb.daointerface.DepotListDao;
import com.nextgentele.busvalidatorv2.roomdb.daointerface.EntrySjtDao;
import com.nextgentele.busvalidatorv2.roomdb.daointerface.ExitSjtDao;
import com.nextgentele.busvalidatorv2.roomdb.daointerface.SjtTicketListDao;
import com.nextgentele.busvalidatorv2.roomdb.daointerface.StopsDao;
import com.nextgentele.busvalidatorv2.roomdb.tables.BaseFareList;
import com.nextgentele.busvalidatorv2.roomdb.tables.TripDetails;


@Database(entities = {ModelEntrySjtTicket.class, ModelExitSjtTicket.class,ModelListActiveSjtTicketPayload.class,BaseFareList.class,Stop.class,ModelListDepotResponsePayload.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DepotListDao depotListDao();
    public abstract StopsDao stopsDao();
    public abstract BaseFareListDao baseFareListDao();
    public abstract SjtTicketListDao sjtTicketListDao();
    public abstract EntrySjtDao entrySjtDao();
    public abstract ExitSjtDao exitSjtDao();

    private static AppDatabase INSTANCE;

    public static final String NAME = "myBusDataBase.db";

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.NAME)
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }



    public static void destroyInstance() {
        INSTANCE = null;
    }

}

