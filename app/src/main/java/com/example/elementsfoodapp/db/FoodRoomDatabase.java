package com.example.elementsfoodapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class}, version = 2, exportSchema = false)
public abstract class FoodRoomDatabase extends RoomDatabase {

    public abstract FoodDao foodDao();
    private static FoodRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static FoodRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FoodRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FoodRoomDatabase.class, "food_database")
                            .createFromAsset("database/foodelements_data.db")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

   /*private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more foods, just add them.
                // If we have no foods, then create initial food(s).
                FoodDao dao = INSTANCE.foodDao();
                if (dao.getAnyFood().length < 1) {
                    String[] foodNames = {"Apfel", "Birne", "Ananas", "Banane", "Kiwi", "Mango",
                    "Brokkoli", "Karotte", "Haferflocken", "Tomate", "Milch", "Kohlrabi"};
                    for (String s : foodNames) {
                        Food food = new Food(s, "test", "test","test",
                            "test","test","test");
                        dao.insert(food);
                    }
                }
            });
        }
    };*/
