package com.viralops.touchlessfoodordering.Support;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.viralops.touchlessfoodordering.ui.Acitities.Login_Activity;


/**
 * Created by simran on 3/25/2018.
 */
public class SessionManagerFCM {
    SharedPreferences pref;
    Context context;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "touchfcm";

   private static final String Token="Token";
    private static final String PORCH_NAME="porch_name";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String NAME = "name";
    private static final String DRIVER_ID = "driverid";
    private static final String PORCH_ID = "porchid";
    private static final String ACCESSTOKEN = "accesstoken";
    private static final String HOTEL_ID = "hotelid";
    private static final String Header_color = "header_color";
    private static final String UserName = "username";

    public  String getToken() {
        return pref.getString(Token,"");
    }
    public  void setToken(String Token1) {
         editor.putString(Token,Token1);
        editor.commit();

    }

    public  String getHeadercolor() {
        return pref.getString(Header_color,"");


    }

    public  String getUserName() {
        return pref.getString(UserName,"");
    }

    public  String getBackgroundColor() {
        return pref.getString(Background_Color,"");
    }

    public  String getSideMEnuColor() {
        return pref.getString(SideMEnu_Color,"");
    }

    public  String getFontSize() {
        return pref.getString(Font_Size,"");
    }

    public  String getFont_Family() {
        return pref.getString(Font_Family,"");
    }

    public  String getMain_Color() {
        return pref.getString(Main_Color,"");
    }

    public  String getFontColor() {
        return pref.getString(Font_Color,"");
    }
    public  void setHeader_color(String id) {
        editor.putString(Header_color,id);
        editor.commit();
    }

    public  void setBackground_Color(String id) {
        editor.putString(Background_Color,id);
        editor.commit();
    }

    public  void setSideMEnu_Color(String id) {
        editor.putString(SideMEnu_Color,id);
        editor.commit();
    }

    public  void setFont_Size(String id) {
        editor.putString(Font_Size,id);
        editor.commit();
    }

    public  void setFont_Family(String id) {
        editor.putString(Font_Family,id);
        editor.commit();
    }

    public  void setMain_Color(String id) {
        editor.putString(Main_Color,id);
        editor.commit();
    }

    public  void setFontColor(String id) {
        editor.putString(Font_Color,id);
        editor.commit();
    }

    private static final String Background_Color = "background_color";
    private static final String SideMEnu_Color = "sidenEnu_Color";
    private static final String Font_Size = "font_size";
    private static final String Font_Family = "font_family";
    private static final String Main_Color = "main_color";
    private static final String Font_Color = "font_color";

    public SharedPreferences getPref() {
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }

    public  String getHotelId() {
        return pref.getString(HOTEL_ID,null);
    }
    public  void setHotelId(String id) {
        editor.putString(HOTEL_ID,id);
        editor.commit();
    }
    public  String getACCESSTOKEN() {
        return pref.getString(ACCESSTOKEN,null);
    }
    public  void setACCESSTOKEN(String id) {
        editor.putString(ACCESSTOKEN,id);
        editor.commit();
    }
    public  String getPorchId() {
        return pref.getString(PORCH_ID,null);
    }
    public  void setPorchId(String id) {
        editor.putString(PORCH_ID,id);
        editor.commit();
    }

    public  String getDriverId() {
        return pref.getString(DRIVER_ID,null);
    }
    public  void setDriverId(String driverid) {
        editor.putString(DRIVER_ID,driverid);
        editor.commit();
    }


    public  String getNAME() {
        return pref.getString(NAME,null);
    }
    public  void setNAME(String name) {
         editor.putString(NAME,name);
        editor.commit();
    }
    public  void setPorchName(String porch) {
         editor.putString(PORCH_NAME,porch);
        editor.commit();

    } public  void setUserName(String username) {
         editor.putString(UserName,username);
        editor.commit();

    }

    public  String getPorchName() {
        return pref.getString(PORCH_NAME,null);
    }


    public  Boolean getIsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public SessionManagerFCM(Context context) {
        this.context=context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }
    public void logoutsession(){

        Intent  intent=new Intent(context, Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        editor.clear();
        editor.commit();
    }
}
