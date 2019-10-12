package zephan.fedex;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "COUNTRIES";

    // Table columns
    public static final String trackingNum = "trackingNum";
    public static final String startCity = "startCity";
    public static final String endCity = "endCity";
    public static final String weight = "weight";
    public static final String alreadySorted = "alreadySorted";
    public static final String currentPath = "description";
    public static final String fastestPath = "description";
    public static final String timeOfTravel = "description";

    // Database Information
    static final String DB_NAME = "ZEPHAN_Packages.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + trackingNum
            + " INTEGER PRIMARY KEY, " + startCity + " INTEGER NOT NULL, " + endCity
            + " INTEGER NOT NULL, " + weight + " INTEGER, "
            + alreadySorted + " INTEGER NOT NULL, " + currentPath + " TEXT, " + fastestPath + " TEXT NOT NULL, "
            + timeOfTravel + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
