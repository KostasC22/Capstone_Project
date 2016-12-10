package com.havistudio.myredditcp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kostas on 01/10/2016.
 */

public class SubRedditContract {

    public static final String CONTENT_AUTHORITY = "com.havistudio.myredditcp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SUBREDDIT = "subreddit";

    public static final class SubRedditEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBREDDIT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBREDDIT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBREDDIT;

        public static final String TABLE_NAME = "subreddit";

        public static final String COLUMN_DOMAIN = "domain";
        public static final String COLUMN_BANNED_BY = "bannedby";
        public static final String COLUMN_MEDIA_EMBED = "mediaembed";
        public static final String COLUMN_SUBREDDIT = "subreddit";
        public static final String COLUMN_SELFTEXT_HTML = "selftexthtml";
        public static final String COLUMN_SELFTEXT = "selftext";
        public static final String COLUMN_LIKES = "likes";
        public static final String COLUMN_SUGGESTED_SORT = "suggestedsort";
        public static final String COLUMN_USER_REPORTS = "userreports";
        public static final String COLUMN_SECURE_MEDIA = "securemedia";
        public static final String COLUMN_LINK_FLAIR_TEXT = "linkflairtext";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_GILDED = "gilded";
        public static final String COLUMN_ARCHIVED = "archived";
        public static final String COLUMN_CLICKED = "clicked";
        public static final String COLUMN_REPORT_REASONS = "reportreasons";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_MEDIA = "media";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_APPROVED_BY = "approvedby";
        public static final String COLUMN_OVER_18 = "overeighteen";
        public static final String COLUMN_HIDDEN = "hidden";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_SUBREDDIT_ID = "subredditid";
        public static final String COLUMN_EDITED = "edited";
        public static final String COLUMN_LINK_FLAIR_CSS_CLASS = "linkflaircssclass";
        public static final String COLUMN_AUTHOR_FLAIR_CSS_CLASS = "authorflaircssclass";
        public static final String COLUMN_DOWNS = "downs";
        public static final String COLUMN_MOD_REPORTS = "modreports";
        public static final String COLUMN_SECURE_MEDIA_EMBED = "securemediaembed";
        public static final String COLUMN_SAVED = "saved";
        public static final String COLUMN_REMOVAL_REASON = "removalreason";
        public static final String COLUMN_IS_SELF = "isself";
        public static final String COLUMN_HIDE_SCORE = "hidescore";
        public static final String COLUMN_PERMALINK = "permalink";
        public static final String COLUMN_LOCKED = "locked";
        public static final String COLUMN_STICKIED = "stickied";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_AUTHOR_FLAIR_TEXT = "authorflairtext";
        public static final String COLUMN_QUARANTINE = "quarantine";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CREATED_UTC = "createdutc";
        public static final String COLUMN_UPS = "ups";
        public static final String COLUMN_NUM_COMMENTS = "numcomments";
        public static final String COLUMN_VISITED = "visited";
        public static final String COLUMN_NUM_REPORTS = "numreports";
        public static final String COLUMN_DISTINGUISHED = "distinguished";
        public static final String COLUMN_SUBSCRIBE = "subscribe";

        public static Uri buildSubRedditUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
