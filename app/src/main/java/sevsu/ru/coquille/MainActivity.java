package sevsu.ru.coquille;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void onClassificationButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, PollingActivity.class);
        startActivity(intent);
    }

    public void onDirectoryButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, DigestActivity.class);
        startActivity(intent);
    }

    public void onMapButtonClick(View view) {
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION=0;
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION);
        } else {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }
    }

    public void onAboutButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                } else {
                    // permission denied
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.alert, Toast.LENGTH_SHORT);
                    toast.show();
                }
                return;
        }
    }
}


