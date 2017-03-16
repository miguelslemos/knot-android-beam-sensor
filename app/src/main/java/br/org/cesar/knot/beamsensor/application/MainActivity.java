package br.org.cesar.knot.beamsensor.application;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.application.model.BeamSensorFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        BeamSensorFilter f = new BeamSensorFilter();
        f.build("CDF","true", BeamSensorFilter.FilterCompareValueMode.Equal, BeamSensorFilter.FilterLinkType.Or,"abc",1, BeamSensorFilter.FilterCompareValueMode.Equal);
        f.build("CDF","true", BeamSensorFilter.FilterCompareValueMode.Equal, BeamSensorFilter.FilterLinkType.And,"abc",1, BeamSensorFilter.FilterCompareValueMode.Equal);
        try {
            JSONObject query = f.getQuery();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
