package com.example.weatherApp.helper_classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <LocalDataType, RemoteDataType> fetchAndSyncData(
    getLocalData: () -> LiveData<LocalDataType>, // פונקציה שמביאה נתונים ממאגר מקומי
    fetchRemoteData: suspend () -> DataStatus<RemoteDataType>, // פונקציה שמביאה נתונים ממקור מרוחק (API)
    saveRemoteDataLocally: suspend (RemoteDataType) -> Unit // פונקציה ששומרת נתונים מרוחקים למאגר המקומי
): LiveData<DataStatus<LocalDataType>> = liveData(Dispatchers.IO) {

    // מודיעים שהתחיל תהליך טעינה
    emit(DataStatus.loading())

    // טוענים את הנתונים המקומיים ומעדכנים את המסך
    val localDataSource = getLocalData().map { DataStatus.success(it) }
    emitSource(localDataSource)

    // מביאים נתונים ממקור מרוחק
    val remoteDataResponse = fetchRemoteData()

    if (remoteDataResponse.status is Success) {
        // אם הצליח להביא נתונים מרוחקים - שמירתם למאגר המקומי
        saveRemoteDataLocally(remoteDataResponse.status.data!!)
    } else if (remoteDataResponse.status is Error) {
        // אם הייתה שגיאה, מודיעים על שגיאה ומחזירים את הנתונים המקומיים
        emit(DataStatus.error(remoteDataResponse.status.message))
        emitSource(localDataSource)
    }
}

//המחלקה הזאת מטפלת בכך שיהיה סינכרון בין הנתונים השמורים ב-room לבין מה שקורה בשרת של ה-API
//הוא גם מטפל במצב שאין אינטרנט ומציג את הנתונים האחרונים שנשמרו
//זה חשוב לחווית משתמש שהוא יראה משהו ולא מסך ריק ברגע שנכנס לאפליקציה עד שהיא מתעדכנת

