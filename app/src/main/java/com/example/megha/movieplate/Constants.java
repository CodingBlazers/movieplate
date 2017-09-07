package com.example.megha.movieplate;

/**
 * Created by megha on 14/08/17.
 */

public interface Constants {

    String OMDB_API_KEY = "73871e7";

    String TYPE = "type";
    String MOVIE = "movie";
    String TV_SHOWS = "tv";

    // Boolean to tell that Api key is present in SP or not
    String BOOLEAN_API_KEY_PRESENT = "ApiKeyInSP";

    // Passing arguments from fragment to child fragments
    String MOVIE_TO_LINEAR_LAYOUT_FRAGMENT = "Movies";
    String TV_TO_LINEAR_LAYOUT_FRAGMENT = "TVList";
    String CELEBS_TO_LINEAR_LAYOUT_FRAGMENT = "Celebs";
    String SINGLE_MOVIE_DETAILS = "SingleMovieDetails";
    String SINGLE_TV_SHOW_DETAILS = "SingleTvShowDetails";
    String TO_LINEAR_LAYOUT_FRAGMENT = "LinearLayoutFragments";

    String ALL_MOVIE_DETAILS = "AllMovieDetails";
    String ALL_TV_SHOW_DETAILS = "AllTvShowDteails";


    // name of file of shared preferences
    String SHARED_PREFERENCE = "MoviePlate";
    String USER_NAME="UserName";

    // Represents value stored in SP corresponding to access token , session Id and user Id
    String SESSION_ID_SP = "SessionId";
    String ID_SP = "UserId";
    String ACCESS_TOKEN_SP = "AccessToken";

    String V4_ACCESS_TOKEN_SP = "AccessToken_V4";

    // These are constants for SP which checks what we have been granted and verified for sign in case (Booleans in SP)
    String BOOLEAN_ACCESS_TOKEN_TAKEN_V4 = "AccessTokenTaken_V4";
    String BOOLEAN_ACCESS_TOKEN_TAKEN = "AccessTokenTaken";
    String BOOLEAN_ACCESS_TOKEN_VERIFIED = "AccessTokenVerified";
    String BOOLEAN_SESSION_ID_GRANTED = "SessionIdGranted";
    String BOOLEAN_SIGNED_IN = "SignedIn";
    String BOOLEAN_GET_ACCOUNT_ID = "AccountId";

    String SEARCH_TITLE = "Title";
    String DETAIL_ACTIVITY_TITLE = "Title";

}
