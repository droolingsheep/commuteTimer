package com.droolingsheep.commutetimer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            CommuteRecord.newInstance(this);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DirectionFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DirectionFragment extends Fragment implements View.OnClickListener {

        private Button mHomeButton;
        private Button mWorkButton;
        private Button mViewButton;

        public DirectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mHomeButton = (Button) rootView.findViewById(R.id.home_button);
            mHomeButton.setOnClickListener(this);
            mWorkButton = (Button) rootView.findViewById(R.id.work_button);
            mWorkButton.setOnClickListener(this);
            mViewButton = (Button) rootView.findViewById(R.id.view_button);
            mViewButton.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v) {
            Direction arg = null;
            if (v == mWorkButton) {
                arg = Direction.WORK;
            } else if (v == mHomeButton) {
                arg = Direction.HOME;
            } else if (v == mViewButton) {
                getFragmentManager().beginTransaction().replace(R.id.container, HistoryFragment.newInstance()).commit();
            }
            if (arg != null) {
                CommuteRecord.getInstance().recordTime(Step.LEAVE);
                getFragmentManager().beginTransaction().replace(R.id.container, AtStopFragment.newInstance(arg)).commit();
            }
        }
    }
}
