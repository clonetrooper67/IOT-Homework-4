package zephan.fedex;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class AddingPackageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_package);
    }

    public void submitButtonPressed(View view) {

        LinkedList<Thread> threads = new LinkedList<Thread>();

        EditText text69 = (EditText) findViewById(R.id.startingCity);
        String bleh = text69.getText().toString();
        int startCity = Integer.parseInt(bleh);

        EditText text70 = (EditText) findViewById(R.id.endCity);
        String bleh1 = text70.getText().toString();
        int endCity = Integer.parseInt(bleh1);

        EditText text71 = (EditText) findViewById(R.id.weight);
        String bleh2= text71.getText().toString();
        int weight = Integer.parseInt(bleh2);

        MainActivity.parcelArray.add(new Package(MainActivity.parcelArray.size(), startCity, endCity, weight));
//		Clear the database
        if (MainActivity.parcelArray.size() > 15) {
            MainActivity.DB.delete();
            MainActivity.parcelArray.clear();
            Package temp = new Package(MainActivity.parcelArray.size(), startCity, endCity, weight);
            MainActivity.parcelArray.add(temp);
        }

        // Go forth and make the thread
        for (int i = 0; i < MainActivity.parcelArray.size(); i++) {
            if (!MainActivity.parcelArray.get(i).alreadySorted) {
                TheBigIdea object = new TheBigIdea(MainActivity.parcelArray.get(i), i);
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

        int trackingNum = MainActivity.parcelArray.size() -1;

        String charTrackingNum = Integer.toString(trackingNum);

        Context context = getApplicationContext();
        CharSequence text = "Package Created With Tracking Number: " + charTrackingNum;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        finish();
    }

}
