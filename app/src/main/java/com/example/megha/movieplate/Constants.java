package com.example.megha.movieplate;

/**
 * Created by megha on 14/08/17.
 */

public interface Constants {

    String API_KEY = "ApiKey";

    String OMDB_API_KEY = "73871e7";

    // Boolean to tell that Api key is present in SP or not
    String BOOLEAN_API_KEY_PRESENT = "ApiKeyInSP";

    // Passing arguments from home activity to various fragments
    String MOVIE_URL_API_KEY = "MovieUrlApiKey";
    String TV_URL_API_KEY = "TVUrlApiKey";
    String CELEBS_URL_API_KEY = "CelebsApiKey";
    String WATCHLIST_URL_API_KEY = "WatchlistApiKey";
    String WATCHLIST_URL_SESSION_ID = "WatchlistSessionId";
    String WATCHLIST_URL_USER_ID = "WatchlistUserId";
    String WATCHLIST_MOVIE_DETAILS="WatchListMovieDetails";

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

    // These are constants for SP which checks what we have been granted and verified for sign in case (Booleans in SP)
    String BOOLEAN_ACCESS_TOKEN_TAKEN = "AccessTokenTaken";
    String BOOLEAN_ACCESS_TOKEN_VERIFIED = "AccessTokenVerified";
    String BOOLEAN_SESSION_ID_GRANTED = "SessionIdGranted";
    String BOOLEAN_SIGNED_IN = "SignedIn";
    String BOOLEAN_GET_ACCOUNT_ID = "AccountId";

    String SEARCH_TITLE = "Title";
    String DETAIL_ACTIVITY_TITLE = "Title";

}
