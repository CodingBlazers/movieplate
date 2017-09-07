package com.example.megha.movieplate.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.megha.movieplate.Constants;

/**
 * Created by megha on 14/08/17.
 */

public class SharedPreferencesUtils implements Constants{

    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getIDKey(){
        return sharedPreferences.getString(ID_SP, null);
    }

    public String getUsername(){
        return sharedPreferences.getString(USER_NAME, null);
    }

    public String getSessionIDKey(){
        return sharedPreferences.getString(SESSION_ID_SP, null);
    }

    public boolean getState(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void setState(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setAccessToken(String token){
        editor.putString(ACCESS_TOKEN_SP, token);
        editor.commit();
        editor.putBoolean(BOOLEAN_ACCESS_TOKEN_TAKEN, true);
        editor.commit();
    }

    public void setAccessTokenFalse(){
        editor.putBoolean(BOOLEAN_ACCESS_TOKEN_TAKEN, false);
        editor.commit();
    }

    public void setAccessTokenVerifiedFalse(){
        editor.putBoolean(BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
        editor.commit();
    }

    public void setSessionIDGrantedFalse(){
        editor.putBoolean(BOOLEAN_SESSION_ID_GRANTED, false);
        editor.commit();
    }

    public void setAccessTokenVerified(){
        editor.putBoolean(BOOLEAN_ACCESS_TOKEN_VERIFIED, true);
        editor.commit();
    }

    public void setSessionID(String sessionID){
        editor.putString(SESSION_ID_SP, sessionID);
        editor.commit();
        editor.putBoolean(BOOLEAN_SESSION_ID_GRANTED, true);
        editor.commit();
        editor.putBoolean(BOOLEAN_SIGNED_IN, true);
        editor.commit();
    }

    public void setAccountDetails(String id, String username){
        editor.putString(ID_SP, id);
        editor.commit();
        editor.putBoolean(BOOLEAN_GET_ACCOUNT_ID, true);
        editor.commit();
        editor.putString(USER_NAME, username);
        editor.commit();
    }

    public void setAccountIDFalse(){
        editor.putBoolean(BOOLEAN_GET_ACCOUNT_ID, false);
        editor.commit();
    }

    public void setSignedOut(){
        editor.putBoolean(BOOLEAN_SIGNED_IN, false);
        editor.commit();
        editor.putBoolean(BOOLEAN_ACCESS_TOKEN_TAKEN, false);
        editor.commit();
        editor.putBoolean(BOOLEAN_SESSION_ID_GRANTED,false);
        editor.commit();
    }

    public boolean isSignedIn(){
        return sharedPreferences.getBoolean(BOOLEAN_SIGNED_IN, false);
    }

}
