package ar.com.tzulberti.archerytraining.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.com.tzulberti.archerytraining.database.migrations.DatabaseMigration4;
import ar.com.tzulberti.archerytraining.database.migrations.DatabaseMigration5;
import ar.com.tzulberti.archerytraining.database.migrations.DatabaseMigration6;
import ar.com.tzulberti.archerytraining.database.migrations.DatabaseMigration7;
import ar.com.tzulberti.archerytraining.database.migrations.DatabaseMigration10;
import ar.com.tzulberti.archerytraining.database.migrations.IDatbasseMigration;

/**
 * Created by tzulberti on 4/18/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "archery_training.db";

    protected static final int DATABASE_VERSION = 11;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TablesCreator tablesCreator = new TablesCreator();
        tablesCreator.createAll(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (! db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("foobar", String.format("OldVersion: %s, newVersion: %s", oldVersion, newVersion));
        // Get all the existing database migrations
        List<IDatbasseMigration> existingMigrations = new ArrayList<>();
        existingMigrations.add(new DatabaseMigration4());
        existingMigrations.add(new DatabaseMigration5());
        existingMigrations.add(new DatabaseMigration6());
        existingMigrations.add(new DatabaseMigration7());
        existingMigrations.add(new DatabaseMigration10());

        for (IDatbasseMigration databaseMigration : existingMigrations) {
            int migrationVersion = databaseMigration.getCurentVersion();
            if (oldVersion < migrationVersion && migrationVersion < newVersion) {
                databaseMigration.executeMigration(db);
            }
        }
    }


}
