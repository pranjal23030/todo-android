package com.example.namiminiproject.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.namiminiproject.database.dao.TaskDao;
import com.example.namiminiproject.database.dao.UserDao;
import com.example.namiminiproject.database.entities.Task;
import com.example.namiminiproject.database.entities.User;

@Database(entities = {User.class, Task.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    static AppDatabase INSTANCE;
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "todo_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }

    // migration upon second change
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Sql statement to create the new table
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS tasks("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                            + "title TEXT NOT NULL, "
                            + "description TEXT NOT NULL, "
                            + "priority TEXT NOT NULL, "
                            + "status TEXT NOT NULL, "
                            + "dueDate TEXT NOT NULL, "
                            + "dueTime TEXT NOT NULL"
                            + ")"
            );
        }
    };
}
