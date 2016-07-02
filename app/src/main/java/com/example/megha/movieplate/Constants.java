package com.example.megha.movieplate;

/**
 * Created by Megha on 31-03-2016.
 */

// Class to store all strings used in app
public class Constants {
    public static String API_KEY = "ApiKey";

    // Boolean to tell that Api key is present in SP or not
    public static String BOOLEAN_API_KEY_PRESENT = "ApiKeyInSP";

    // Passing arguments from home activity to various fragments
    public static String MOVIE_URL_API_KEY = "MovieUrlApiKey";
    public static String TV_URL_API_KEY = "TVUrlApiKey";
    public static String CELEBS_URL_API_KEY = "CelebsApiKey";
    public static String WATCHLIST_URL_API_KEY = "WatchlistApiKey";
    public static String WATCHLIST_URL_SESSION_ID = "WatchlistSessionId";
    public static String WATCHLIST_URL_USER_ID = "WatchlistUserId";
    public static final int RESULT_SUCCESS=0;
    public static final int INTERNET_CONNECTION_FAILURE=2;
    public static final int OTHER_SERVER_ERROR=1;



    // Passing arguments from fragment to child fragments
    public static String MOVIE_TO_LINEAR_LAYOUT_FRAGMENT = "Movies";
    public static String TV_TO_LINEAR_LAYOUT_FRAGMENT = "TV";
    public static String CELEBS_TO_LINEAR_LAYOUT_FRAGMENT = "Celebs";
    public static String SINGLE_MOVIE_DETAILS = "SingleMovieDetails";
    public static String SINGLE_TV_SHOW_DETAILS = "SingleTvShowDetails";

    // name of file of shared preferences
    public static String SHARED_PREFERENCE = "MoviePlate";

    // Represents value stored in SP corresponding to access token , session Id and user Id
    public static String SESSION_ID_SP = "SessionId";
    public static String ID_SP = "UserId";
    public static String ACCESS_TOKEN_SP = "AccessToken";

    // These are constants for SP which checks what we have been granted and verified for sign in case (Booleans in SP)
    public static String BOOLEAN_ACCESS_TOKEN_TAKEN = "AccessTokenTaken";
    public static String BOOLEAN_ACCESS_TOKEN_VERIFIED = "AccessTokenVerified";
    public static String BOOLEAN_SESSION_ID_GRANTED = "SessionIdGranted";
    public static String BOOLEAN_SIGNED_IN = "SignedIn";
    public static String BOOLEAN_GET_ACCOUNT_ID = "AccountId";

}
