package zephan.fedex;
import android.util.Log;

import java.util.LinkedList;
import java.util.TimerTask;

public class Chronos extends TimerTask {

    public void run() {
    Log.d("Test", "I'm alive!");
        LinkedList<Integer> path = new LinkedList<>();
        LinkedList<Integer> path2 = new LinkedList<>();
        LinkedList<Integer> time = new LinkedList<>();

        for (int i = 0; i < MainActivity.parcelArray.size(); i++) {
//			Set the package's linked lists to our temporary ones
            time = MainActivity.parcelArray.get(i).getTimeOfTravel();
            path = MainActivity.parcelArray.get(i).getFastestPath();
            path2 = MainActivity.parcelArray.get(i).getCurrentPath();
//			Move the next city into our current path
            if (path.size() > 0) {
                path2.add(path.poll());
                int index = time.size() - 1;

                int numGot = time.get(index) + 1;

                time.add(numGot);
            }
//			Write the changes back to the package
            MainActivity.parcelArray.get(i).setFastestPath(path);
            MainActivity.parcelArray.get(i).setCurrentPath(path2);
            MainActivity.parcelArray.get(i).setTimeOfTravel(time);

            try {
                MainActivity.DB.update(MainActivity.parcelArray.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

