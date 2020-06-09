package com.example.individualapplication.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Method om de SleepNight class met room te gebruiken.
 */

@Dao
interface SleepDatabaseDao {

    @Insert
    fun insert (night: SleepNight)

    /**
     * update de row met de value die al geset is
     * en verander de oude value naar de nieuwe
     */
    @Update
    fun update(night: SleepNight)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long) : SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTnight() : SleepNight?

}