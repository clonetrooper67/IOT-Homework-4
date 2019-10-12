package zephan.fedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Package currentPackage) {

        String currentPath = "";
        String fastestPath = "";
        String timeOfTravel = "";
//			Make the needed strings to write the lists to the database
        for (int i = 0; i < currentPackage.currentPath.size(); i++) {
            currentPath = currentPath + "," + Integer.toString(currentPackage.currentPath.get(i));
        }

        for (int i = 0; i < currentPackage.fastestPath.size(); i++) {
            fastestPath = fastestPath + "," + Integer.toString(currentPackage.fastestPath.get(i));
        }

        for (int i = 0; i < currentPackage.timeOfTravel.size(); i++) {
            timeOfTravel = timeOfTravel + "," + Integer.toString(currentPackage.timeOfTravel.get(i));
        }

        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.trackingNum, currentPackage.trackingNumber);
        contentValue.put(DatabaseHelper.startCity, currentPackage.startCity);
        contentValue.put(DatabaseHelper.endCity, currentPackage.endCity);
        contentValue.put(DatabaseHelper.weight, currentPackage.weight);
        contentValue.put(DatabaseHelper.alreadySorted, 1);
        contentValue.put(DatabaseHelper.currentPath, currentPath);
        contentValue.put(DatabaseHelper.fastestPath, fastestPath);
        contentValue.put(DatabaseHelper.timeOfTravel, timeOfTravel);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch(Package currentPackage) {
        String[] columns = new String[] { DatabaseHelper.trackingNum, DatabaseHelper.startCity,
                DatabaseHelper.endCity, DatabaseHelper.weight, DatabaseHelper.alreadySorted, DatabaseHelper.currentPath,
                DatabaseHelper.fastestPath, DatabaseHelper.timeOfTravel};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(Package currentPackage) {

        String currentPath = "";
        String fastestPath = "";
        String timeOfTravel = "";
//		Make the needed strings to write the lists to the database
        for (int i = 0; i < currentPackage.currentPath.size(); i++) {
            currentPath = currentPath + "," + Integer.toString(currentPackage.currentPath.get(i));
        }

        for (int i = 0; i < currentPackage.fastestPath.size(); i++) {
            fastestPath = fastestPath + "," + Integer.toString(currentPackage.fastestPath.get(i));
        }

        for (int i = 0; i < currentPackage.timeOfTravel.size(); i++) {
            timeOfTravel = timeOfTravel + "," + Integer.toString(currentPackage.timeOfTravel.get(i));
        }


        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.currentPath, currentPath);
        contentValues.put(DatabaseHelper.fastestPath, fastestPath);
        contentValues.put(DatabaseHelper.timeOfTravel, timeOfTravel);

        database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.trackingNum + " = " + currentPackage.trackingNumber, null);
    }

    public void delete(int trackingNum) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.trackingNum + "=" + trackingNum, null);
    }

}