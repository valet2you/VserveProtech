package com.viralops.touchlessfoodordering.API;


import android.view.Menu;

import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.ConnectHistory;
import com.viralops.touchlessfoodordering.Model.Connect_Header;
import com.viralops.touchlessfoodordering.Model.Dashboard;
import com.viralops.touchlessfoodordering.Model.HomeViewModel;
import com.viralops.touchlessfoodordering.Model.IRD_Menu;
import com.viralops.touchlessfoodordering.Model.LaundryOrderHistory1;
import com.viralops.touchlessfoodordering.Model.Laundry_Dashboard;
import com.viralops.touchlessfoodordering.Model.Laundry_Header;
import com.viralops.touchlessfoodordering.Model.Login;
import com.viralops.touchlessfoodordering.Model.OrderHistory;
import com.viralops.touchlessfoodordering.Model.Spa_dashboard;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface GerritAPI {
    @FormUrlEncoded
    @POST(BuildConfig.login)
    Call<Login> SignIn(@Field("username") String username, @Field("password") String password, @Field("device_token") String device_token,
                       @Field("device_type") String device_type);
    @GET(BuildConfig.logout)
    Call<Action> logout(@Header("Authorization") String Authorization);

//--------------------Ird-------------------------//
    @GET(BuildConfig.ird_menu)
    Call<Menu> getMenu(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> getEnabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getdisabled(@Header("Authorization") String Authorization, @Url String url);
    @GET(BuildConfig.ird_menu)
    Call<IRD_Menu> getIRDMenu(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> getcomplimentaryEnabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcomplimentaryDisabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getItemEnabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getItemDisabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategoryEnabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategorydisabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategoryEnabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddondisabled(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonenable(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemenable(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemdisable(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategorydisabled(@Header("Authorization") String Authorization, @Url String url);

    @GET(BuildConfig.get_orders)
    Call<HomeViewModel> getOrders(@Header("Authorization") String Authorization);

    @GET(BuildConfig.get_orders)
    Call<Dashboard> getOrders1(@Header("Authorization") String Authorization);

    @PUT()
    Call<Action> OrderAccepted(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> OrderDispatched(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> OrderCleared(@Url String url, @Header("Authorization") String Authorization);

    @GET(BuildConfig.get_order_header)
    Call<com.viralops.touchlessfoodordering.Model.Header> getHeader(@Header("Authorization") String Authorization);

    @GET(BuildConfig.get_order_history)
    Call<OrderHistory> getHistory(@Header("Authorization") String Authorization, @Query("show") String show);
    //----------------End of IRD-------------------------------------------//

    //----------------Minibar----------------------------------------------//
    @GET(BuildConfig.minibar_menu)
    Call<Menu> getMenuMinibar(@Header("Authorization") String Authorization);
      @PUT()
    Call<Action> getEnabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getisabledMinibar(@Header("Authorization") String Authorization, @Url String url);
   @PUT()
    Call<Action> getItemEnabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getItemDisabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategoryEnabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategorydisabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategoryEnabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddondisabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonenableMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemenableMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemdisableMinibar(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategorydisabledMinibar(@Header("Authorization") String Authorization, @Url String url);
    @GET(BuildConfig.minibar_get_orders)
    Call<Dashboard> Minibar_getOrders(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> Minibar_OrderAccepted(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> Minibar_OrderDispatched(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> Minibar_OrderCleared(@Url String url, @Header("Authorization") String Authorization);

    @GET(BuildConfig.minibar_get_order_header)
    Call<com.viralops.touchlessfoodordering.Model.Header> Minibar_getHeader(@Header("Authorization") String Authorization);

    @GET(BuildConfig.minibar_get_order_history)
    Call<OrderHistory> Minibar_getHistory(@Header("Authorization") String Authorization, @Query("show") String show);



    //--------------End of minibar----------------------------------------//

 //----------------Laundry----------------------------------------------//
    @GET(BuildConfig.minibar_menu)
    Call<Menu> getMenuLaundry(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> getItemEnabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getItemDisabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategoryEnabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategorydisabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategoryEnabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddondisabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonenableLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemenableLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemdisableLaundry(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategorydisabledLaundry(@Header("Authorization") String Authorization, @Url String url);
    @GET(BuildConfig.laundry_get_orders)
    Call<Laundry_Dashboard> Laundry_getOrders(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> Laundry_OrderAccepted(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> Laundry_OrderDispatched(@Url String url, @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST()
    Call<Action> Laundry_OrderCleared(@Url String url, @Header("Authorization") String Authorization, @Field("stagestage") String stage, @Field("stage_text") String stage_text);

    @GET(BuildConfig.laundry_get_order_header)
    Call<Laundry_Header> Laundry_getHeader(@Header("Authorization") String Authorization);

    @GET(BuildConfig.laundry_get_order_history)
    Call<LaundryOrderHistory1> Laundry_getHistory(@Header("Authorization") String Authorization, @Query("show") String show);



    //--------------End of laundry----------------------------------------//

//----------------Spa----------------------------------------------//
    @GET(BuildConfig.spa_menu)
    Call<Menu> getMenuSpa(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> getItemEnabledspa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getItemDisabledspa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategoryEnabledspa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getcategorydisabledspa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategoryEnabledspa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddondisabledspa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonenablespa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemenablespa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubaddonitemdisablespa(@Header("Authorization") String Authorization, @Url String url);
    @PUT()
    Call<Action> getSubcategorydisabledSpa(@Header("Authorization") String Authorization, @Url String url);
    @GET(BuildConfig.spa_get_orders)
    Call<Spa_dashboard> Spa_getOrders(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> Spa_OrderAccepted(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> Spa_OrderDispatched(@Url String url, @Header("Authorization") String Authorization);
    @FormUrlEncoded
    @POST()
    Call<Action> Spa_OrderCleared(@Url String url, @Header("Authorization") String Authorization, @Field("stage") String stage, @Field("stage_text") String stage_text);

    @GET(BuildConfig.spa_get_order_header)
    Call<com.viralops.touchlessfoodordering.Model.Header> Spa_getHeader(@Header("Authorization") String Authorization);

    @GET(BuildConfig.spa_get_order_history)
    Call<Spa_dashboard> Spa_getHistory(@Header("Authorization") String Authorization, @Query("show") String show);



    //--------------End of SPa----------------------------------------//
//----------------Connect----------------------------------------------//
       @GET(BuildConfig.connect_get_orders)
    Call<ConnectHistory> Connect_getOrders(@Header("Authorization") String Authorization);
    @PUT()
    Call<Action> Connect_OrderAccepted(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> Connect_OrderDispatched(@Url String url, @Header("Authorization") String Authorization);

    @PUT()
    Call<Action> Connect_OrderCleared(@Url String url, @Header("Authorization") String Authorization);

    @GET(BuildConfig.connect_get_order_header)
    Call<Connect_Header> Connect_getHeader(@Header("Authorization") String Authorization);

    @GET(BuildConfig.connect_get_order_history)
    Call<ConnectHistory> Connect_getHistory(@Header("Authorization") String Authorization, @Query("show") String show);



    //--------------End of SPa----------------------------------------//



}
