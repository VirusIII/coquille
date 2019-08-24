package sevsu.ru.coquille;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.HashMap;
import java.util.Map;


public class DigestActivity extends AppCompatActivity {
    LinearLayout llMain;
    String allTypes;
    String[] things;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llMain = findViewById(R.id.llMain);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        // переносим полученное значение выравнивания в LayoutParams
        lParams.gravity = Gravity.CENTER_HORIZONTAL;
        allTypes=getString(R.string.all_types1)+getString(R.string.all_types2)+getString(R.string.all_types3);
        things =allTypes.split("@");
        int USERID = 6000;
        int countID = 0;
        for(int i=0;i<things.length;i+=2)
        {
                final Button btnNew = new Button(this);
                btnNew.setText(things[i]);
                btnNew.setId(USERID + countID++);
                btnNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DigestActivity.this,DigestThingActivity.class);
                        intent.putExtra("sortName", things[((int)btnNew.getId()-6000)*2]);
                        intent.putExtra("sortSpecification", things[((int)btnNew.getId()-6000)*2+1]);
                        startActivity(intent);
                    }
                });
                llMain.addView(btnNew, lParams);
        }

    }
}
