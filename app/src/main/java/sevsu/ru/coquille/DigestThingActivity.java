package sevsu.ru.coquille;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DigestThingActivity extends AppCompatActivity {
    TextView textView;
    ImageView sortImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String filename = getIntent().getStringExtra("sortName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digest_thing);
        textView = findViewById(R.id.sort_name);
        textView.setText(filename);
        textView = findViewById(R.id.sort_specification);
        textView.setText(getIntent().getStringExtra("sortSpecification"));
        sortImage = findViewById(R.id.sort_image);
        //fragment.getResources().getIdentifier(filename, "drawable", "my.project.package");
        try {
            sortImage.setImageResource(R.drawable.class.getField(filename.replace(' ','_').toLowerCase()).getInt(getResources()));
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    R.string.ups, Toast.LENGTH_SHORT).show();
        }


    }
}
