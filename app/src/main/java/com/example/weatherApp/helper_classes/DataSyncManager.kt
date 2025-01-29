package com.example.weatherApp.helper_classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <LocalDataType, RemoteDataType> fetchAndSyncData(
    getLocalData: () -> LiveData<LocalDataType>,  // פונקציה שמביאה נתונים ממאגר מקומי (ROOM)
    fetchRemoteData: suspend () -> RemoteDataType?, // פונקציה שמביאה נתונים מה-API
    saveRemoteDataLocally: suspend (RemoteDataType) -> Unit, // פונקציה ששומרת את הנתונים החדשים ב-ROOM
    onError: (String) -> Unit // פונקציה לטיפול בשגיאות
): LiveData<LocalDataType> = liveData(Dispatchers.IO) {

    // שלב 1: נטען תחילה את הנתונים מה-ROOM
    val localData = getLocalData()
    emitSource(localData)

    try {
        // שלב 2: מביאים נתונים מה-API
        val remoteData = fetchRemoteData()
        if (remoteData != null) {
            saveRemoteDataLocally(remoteData) // שמירת הנתונים החדשים ב-ROOM
        }
    } catch (e: Exception) {
        onError("שגיאה בטעינת הנתונים: ${e.message}") // מעדכן הודעת שגיאה
    }
}
