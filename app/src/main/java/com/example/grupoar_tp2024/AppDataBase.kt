package com.example.grupoar_tp2024

import android.content.Context
import androidx.room.*;

@Database(entities = [UsuarioRegistrado::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioRegistradoDao(): UsuarioRegistradoDao
    companion object{
        private var INSTANCIA: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if(INSTANCIA == null){
                synchronized(this){
                    INSTANCIA = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "usuarioRegistrado_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCIA!!
        }
    }
}