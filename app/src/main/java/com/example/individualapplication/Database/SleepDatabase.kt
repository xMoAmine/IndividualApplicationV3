package com.example.individualapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database die SleepNight informatie stored.
 * een global method aanmaken om toegang tot de
 * databse te krijgen.
 */

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase (){
    /**
     * connects de database met de DAO
     */

    abstract val sleepDatabaseDao : SleepDatabaseDao

    /**
     * je maakt hier een companion object aan, hierdoor kun je functies toevoegen aan de SleepDatabase Class.
     *
     * Bijvoorbeeld: clients kunnen een call naar "SleepDatabase.getInstance(context) sturen
     * om een nieuwe SleepDatabase te instantieren.
     */

    companion object{
        /**
         * INSTANCE laat een reference achter naar de database die wordt gereturned
         * door getInstance.
         *
         * Dit zodat je niet telkens de database moet initialiseren.
         *
         * De value van de volatile variable wordt nooit gecached, en alles wat je werite and read wordt gedaan
         * van en naar de main memory.
         * wijzigingen die worden gemaakt door een thread kunnen dus ook gezien worden door andere threads.
         */

        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(context: Context) : SleepDatabase {


            synchronized(this){

                var instance= INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    //Assign INSTANCE naar de nieuwe database.
                    INSTANCE = instance
                }

                //Return instance
                return instance
            }
        }

    }
}