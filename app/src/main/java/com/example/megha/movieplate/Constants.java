package com.example.megha.movieplate;

/**
 * Created by Megha on 31-03-2016.
 */
public class Constants {
    public static String API_KEY = "ApiKey";
    public static String MOVIE_URL_API_KEY = "MovieUrlApiKey";
    public static String TV_URL_API_KEY="TVUrlApiKey";
    public static String CELEBS_URL_API_KEY="CelebsApiKey";
    public static String MOVIE_TO_LINEAR_LAYOUT_FRAGMENT = "Movies";
    public static String TV_TO_LINEAR_LAYOUT_FRAGMENT="TV";
    public static String CELEBS_TO_LINEAR_LAYOUT_FRAGMENT="Celebs";
    public static String SINGLE_MOVIE_DETAILS = "SingleMovieDetails";
    public static String SINGLE_TV_SHOW_DETAILS="SingleTvShowDetails";

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

}
