package zephan.fedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DatabaseManager extends SQLiteOpenHelper {

    // Table Name
    public static final String DATABASE_NAME = "packages.db";
    // Table Name
    public static final String TABLE_NAME = "parcels";

    // Table columns
    public static final String trackingNumColumn = "trackingNum";
    public static final String startCityColumn = "startCity";
    public static final String endCityColumn = "endCity";
    public static final String weightColumn = "weight";
    public static final String alreadySortedColumn = "alreadySorted";
    public static final String currentPathColumn = "currentPath";
    public static final String fastestPathColumn = "fastestPath";
    public static final String timeOfTravelColumn = "timeOfTravel";


    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + trackingNumColumn
            + " INTEGER PRIMARY KEY, " + startCityColumn + " INTEGER NOT NULL, " + endCityColumn
            + " INTEGER NOT NULL, " + weightColumn + " INTEGER, "
            + alreadySortedColumn + " INTEGER NOT NULL, " + currentPathColumn + " TEXT, " + fastestPathColumn + " TEXT NOT NULL, "
            + timeOfTravelColumn + " TEXT);";

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            db.execSQL(CREATE_TABLE);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS packages_table");
        onCreate(db);
    }


    public void insert(Package currentPackage) {
        SQLiteDatabase db = this.getWritableDatabase();

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

        contentValue.put(trackingNumColumn, currentPackage.trackingNumber);
        contentValue.put(startCityColumn, currentPackage.startCity);
        contentValue.put(endCityColumn, currentPackage.endCity);
        contentValue.put(weightColumn, currentPackage.weight);
        contentValue.put(alreadySortedColumn, 1);
        contentValue.put(currentPathColumn, currentPath);
        contentValue.put(fastestPathColumn, fastestPath);
        contentValue.put(timeOfTravelColumn, timeOfTravel);

        db.insert(TABLE_NAME, null, contentValue);
    }

    public void read() {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = new String[]{trackingNumColumn, startCityColumn,
                endCityColumn, weightColumn, alreadySortedColumn, currentPathColumn,
                fastestPathColumn, timeOfTravelColumn};
        Cursor rs = db.query(TABLE_NAME, columns, null,
                null, null, null, null);
        while (rs.moveToNext()) {
//			Get the data we want from each set
            int trackingNumber = rs.getInt(0);
            int startCity = rs.getInt(1);
            int endCity = rs.getInt(2);
            int weight = rs.getInt(3);

            boolean alreadySorted = bool(rs.getInt(4));

            String currentPath = rs.getString(5);
            String fastestPath = rs.getString(6);
            String timeOfTravel = rs.getString(7);//			Make the linked lists we need
            LinkedList<Integer> currentPathList = new LinkedList<Integer>();
            LinkedList<Integer> fastestPathList = new LinkedList<Integer>();
            LinkedList<Integer> timeOfTravelList = new LinkedList<Integer>();
//			Make string arrays the data received from the database
            String[] currentPathArray = currentPath.split(",");
            String[] fastestPathArray = fastestPath.split(",");
//            String[] timeOfTravelArray = timeOfTravel.split(",");
//			Parse through each Array and push it into the list
            for (int i = 1; i < currentPathArray.length; i++) {
                currentPathList.add(Integer.parseInt(currentPathArray[i]));
            }

            for (int i = 1; i < fastestPathArray.length; i++) {
                fastestPathList.add(Integer.parseInt(fastestPathArray[i]));
            }

//            for (int i = 1; i < timeOfTravelArray.length; i++) {
//                timeOfTravelList.add(Integer.parseInt(timeOfTravelArray[i]));
//            }
//			Make a new package with the information
            Package parcel = new Package(trackingNumber, startCity, endCity, weight);

            parcel.setCurrentPath(currentPathList);
            parcel.setFastestPath(fastestPathList);
//            parcel.setTimeOfTravel(timeOfTravelList);
            parcel.setAlreadySorted(alreadySorted);
//			Push the package to the main array
            MainActivity.parcelArray.add(parcel);
        }
    }

    private boolean bool(int anInt) {
        if (anInt != 0) return true;
        return false;
    }

    public void update(Package currentPackage) {
        SQLiteDatabase db = this.getWritableDatabase();

        String currentParsedPath = "";
        String fastestParsedPath = "";
        String parsedTimeOfTravel = "";

//		Make the needed strings to write the lists to the database
        for (int i = 0; i < currentPackage.currentPath.size(); i++) {
            currentParsedPath = currentParsedPath + "," + Integer.toString(currentPackage.currentPath.get(i));
        }

        for (int i = 0; i < currentPackage.fastestPath.size(); i++) {
            fastestParsedPath = fastestParsedPath + "," + Integer.toString(currentPackage.fastestPath.get(i));
        }

        for (int i = 0; i < currentPackage.timeOfTravel.size(); i++) {
            parsedTimeOfTravel = parsedTimeOfTravel + "," + Integer.toString(currentPackage.timeOfTravel.get(i));
        }


        ContentValues vals = new ContentValues();

        vals.put(currentPathColumn, currentParsedPath);
        vals.put(fastestPathColumn, fastestParsedPath);
        vals.put(timeOfTravelColumn, parsedTimeOfTravel);

        String SQL_ARG = "UPDATE ";

        db.update(TABLE_NAME, vals, trackingNumColumn + "=" + currentPackage.trackingNumber, null);
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

}