package com.nextgentele.busvalidatorv2.apiupdate;




import com.nextgentele.busvalidatorv2.models.ModelConfigurationResponse;
import com.nextgentele.busvalidatorv2.models.ModelDriverLogin;
import com.nextgentele.busvalidatorv2.models.ModelDriverLoginResponse;
import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicketResponse;
import com.nextgentele.busvalidatorv2.models.ModelExitSjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelListBaseFareResponse;
import com.nextgentele.busvalidatorv2.models.ModelListComuterAndDepot;
import com.nextgentele.busvalidatorv2.models.ModelListComuterResponse;
import com.nextgentele.busvalidatorv2.models.ModelListDepotResponse;
import com.nextgentele.busvalidatorv2.models.ModelListRoute;
import com.nextgentele.busvalidatorv2.models.ModelListRouteInfo;
import com.nextgentele.busvalidatorv2.models.ModelListRouteInfoResponse;
import com.nextgentele.busvalidatorv2.models.ModelListRouteResponse;
import com.nextgentele.busvalidatorv2.models.ModelTripStart;
import com.nextgentele.busvalidatorv2.models.ModelTripStartResponse;
import com.nextgentele.busvalidatorv2.models.ModelValidatorInfo;
import com.nextgentele.busvalidatorv2.models.ModelValidatorInfoResponse;
import com.nextgentele.busvalidatorv2.models.ValidateWallet;
import com.nextgentele.busvalidatorv2.models.ValidateWalletResponse;


import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST(EndApi.LOGIN_USER)
    Call<ModelDriverLoginResponse> login(@Body ModelDriverLogin modelDriverLogin);

    @POST(EndApi.LIST_COMMUTER_CATEGORY)
    Call<ModelListComuterResponse> getCommuterCategory(@Body ModelListComuterAndDepot modelListComuterAndDepot);

    @POST(EndApi.LIST_DEPOT)
    Call<ModelListDepotResponse> getDepots(@Body ModelListComuterAndDepot modelListComuterAndDepot);

    @POST(EndApi.LIST_ROUTES)
    Call<ModelListRouteResponse> getRoutes(@Body ModelListRoute modelListRoute);

    @POST(EndApi.ROUTE_INFO)
    Call<ModelListRouteInfoResponse> getRouteInfo(@Body ModelListRouteInfo modelListRoute);

    @POST(EndApi.LIST_BASE_FARE)
    Call<ModelListBaseFareResponse> getListBaseFares(@Body ModelListRouteInfo modelListRoute);

    @POST(EndApi.CONFIGURATION)
    Call<ModelConfigurationResponse> getConfiguration(@Body ModelListRouteInfo modelListRoute);

    @POST(EndApi.VALIDATOR_INFO)
    Call<ModelValidatorInfoResponse> getValidatorInfo(@Body ModelValidatorInfo modelListRoute);

    @POST(EndApi.LIST_ACTIVE_SJT)
    Call<ModelListActiveSjtTicket> getSJTList(@Body ModelListRouteInfo modelListRouteInfo);

    @POST(EndApi.SJT_ENTRY)
    Call<ModelEntrySjtTicketResponse> sjtEntry(@Body ModelEntrySjtTicket modelEntrySjtTickete);

    @POST(EndApi.TRIP_START)
    Call<ModelTripStartResponse> tripStart(@Body ModelTripStart modelTripStart);

    @POST(EndApi.SJT_EXIT)
    Call<ModelEntrySjtTicketResponse> sjtExit(@Body ModelExitSjtTicket modelTripStart);

    @POST(EndApi.VALIDATE_WALLET)
    Call<ValidateWalletResponse> validateWallet(@Body ValidateWallet modelTripStart);


}