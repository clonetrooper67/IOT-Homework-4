package zephan.fedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    public static LinkedList<Package> parcelArray = new LinkedList<Package>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager DB = new DatabaseManager(this);

        LinkedList<Thread> threads = new LinkedList<Thread>();

        parcelArray.push(new Package(0, 4, 8, 1));
        parcelArray.push(new Package(1, 4, 8, 1));
        parcelArray.push(new Package(2, 4, 8, 1));
        parcelArray.push(new Package(3, 4, 8, 1));
        parcelArray.push(new Package(4, 4, 8, 1));
        parcelArray.push(new Package(5, 4, 8, 1));
        parcelArray.push(new Package(6, 4, 8, 1));
        parcelArray.push(new Package(7, 4, 8, 1));
        parcelArray.push(new Package(8, 4, 8, 1));
        parcelArray.push(new Package(9, 4, 8, 1));
        parcelArray.push(new Package(10, 4, 8, 1));
        parcelArray.push(new Package(11, 4, 8, 1));
        parcelArray.push(new Package(12, 4, 8, 1));
        parcelArray.push(new Package(13, 4, 8, 1));
        parcelArray.push(new Package(14, 4, 8, 1));

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

    }
}
