package com.style.probro;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.style.probro.models.PBArticle;

import java.lang.reflect.Type;
import java.util.Date;

public class RoomDataConverters {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public String fromPBArticle(PBArticle pbArticle) {
        if (pbArticle == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.toJson(pbArticle);
    }

    @TypeConverter
    public PBArticle toPBArticle(String pbArticleString) {
        if (pbArticleString == null) {
            return (null);
        }
        Gson gson = new Gson();

       return  gson.fromJson(pbArticleString, PBArticle.class);
    }
}
