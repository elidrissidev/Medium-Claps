package apps.mohamed.mediumclap;

import android.app.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.design.widget.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ClapsView clapsFrame = (ClapsView) findViewById(R.id.clapsView);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		clapsFrame.with(fab);
    }
}
