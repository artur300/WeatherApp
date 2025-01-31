@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
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

/**
 * סיכום הפונקציה:

 * fetchAndSyncData
 * - פונקציה שמבצעת סנכרון בין הנתונים המקומיים ב-ROOM לבין הנתונים המתקבלים מה-API.
 * - תחילה היא מביאה נתונים מהמאגר המקומי, ולאחר מכן מנסה לעדכן אותם עם נתונים מהשרת.

 * פירוט הפרמטרים:
 * - getLocalData: פונקציה שמביאה את הנתונים ממאגר הנתונים המקומי (ROOM).
 * - fetchRemoteData: פונקציה שמביאה נתונים מהשרת באמצעות API.
 * - saveRemoteDataLocally: פונקציה ששומרת את הנתונים שהתקבלו מהשרת בבסיס הנתונים המקומי.
 * - onError: פונקציה שמטפלת בשגיאות במקרה של כשלון בקבלת הנתונים מהשרת.

 * תהליך העבודה:
 * 1. טוען תחילה את הנתונים מהמאגר המקומי (ROOM) ומציג אותם.
 * 2. מנסה להביא נתונים מעודכנים מה-API.
 * 3. אם הנתונים החדשים התקבלו בהצלחה, הם נשמרים במאגר המקומי.
 * 4. אם מתרחשת שגיאה בזמן שליפת הנתונים מה-API, מופיעה הודעת שגיאה.

 * מטרת הפונקציה היא להציג מידע קיים למשתמש במהירות מתוך המאגר המקומי, תוך ניסיון לעדכן אותו עם נתונים חדשים מהשרת.
 */
