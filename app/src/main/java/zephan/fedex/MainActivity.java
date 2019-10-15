package zephan.fedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static LinkedList<Package> parcelArray = new LinkedList<Package>();

    String cities[] = {"Northborough, MA", "Edison, NJ", "Pittsburgh, PA", "Allentown, PA", "Martinsburg, WV",
            "Charlotte, NC", "Atlanta, GA", "Orlando, FL", "Memphis, TN", "Grove City, OH", "Indianapolis, IN",
            "Detroit, MI", "New Berlin, WI", "Minneapolis, MN", "St. Louis, MO", "Kansas, KS", "Dallas, TX",
            "Houston, TX", "Denver, CO", "Salt Lake City, UT", "Phoenix, AZ", "Los Angeles, CA", "Chino, CA",
            "Sacramento, CA", "Seattle, WA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager DB = new DatabaseManager(this);

        LinkedList<Thread> threads = new LinkedList<Thread>();

        parcelArray.add(new Package(0, 0, 12, 1));
        parcelArray.add(new Package(1, 3, 8, 1));
        parcelArray.add(new Package(2, 23, 8, 1));
        parcelArray.add(new Package(3, 4, 8, 1));
        parcelArray.add(new Package(4, 4, 8, 1));
        parcelArray.add(new Package(5, 4, 8, 1));
        parcelArray.add(new Package(6, 4, 8, 1));
        parcelArray.add(new Package(7, 4, 8, 1));
        parcelArray.add(new Package(8, 4, 8, 1));
        parcelArray.add(new Package(9, 4, 8, 1));
        parcelArray.add(new Package(10, 4, 8, 1));
        parcelArray.add(new Package(11, 4, 8, 1));
        parcelArray.add(new Package(12, 4, 8, 1));
        parcelArray.add(new Package(13, 4, 8, 1));
        parcelArray.add(new Package(14, 4, 8, 1));

//		Go ahead and find the path for all of these
        for (int i = 0; i < parcelArray.size(); i++) {
            if (!parcelArray.get(i).alreadySorted) {
                TheBigIdea object = new TheBigIdea(parcelArray.get(i), i);
                object.start();
                threads.push(object);
            }
        }

        //		Join the threads
        try {
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).join();
            }
        } catch (Exception e) {

        }

        Timer timer = new Timer();
        TimerTask task = new Chronos();

//		Move packages every 5 seconds
        timer.schedule(task, 5000, 5000);
    }

    public void trackThePackage(View view) {
        try {
            EditText text69 = (EditText) findViewById(R.id.trackingNum);
            String bleh = text69.getText().toString();
            int index = Integer.parseInt(bleh);

            LinkedList<Integer> test = parcelArray.get(index).getCurrentPath();

            for (int i = 0; i < test.size(); i++) {
                String currentIndex = Integer.toString(test.get(i));
                Log.d("test", currentIndex);
            }

            Context context = getApplicationContext();
            CharSequence text =
                    "Start City: " + cities[parcelArray.get(index).getStartCity()] + "\n"
                            + "End City: " + cities[parcelArray.get(index).getEndCity()] + "\n"
                            + "Path: " + "\n";

            for (int i = 0; i < test.size(); i++) {
                String city = cities[test.get(i)];

                text = text + city + "\n";

            }

            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (Exception e) {
        }

        Log.d("test", "Done");
    }

}