package sevsu.ru.coquille;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PoolThingActivity extends AppCompatActivity {
    String[] answers;
    int i = 0;
    LinearLayout pool_thing;
    TextView textPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_thing);
        answers = getIntent().getStringArrayExtra("answers");
        i = getIntent().getIntExtra("number", 0);
        int max = 0;
        for(String str:answers)
        {

            if(str.length()>max){
                max=str.length();
            }
        }

        StringBuilder set = new StringBuilder("");
        final StringBuilder respondSet = new StringBuilder();
        for (int i = 0; i < max; i++){
            set.append("0");
            respondSet.append("0");
            for(String str:answers){
                if(str.length()==max){
                    if(str.charAt(i) == '1'){
                        set.setCharAt(i,'1');
                        break;
                    }
                }
            }
        }

        pool_thing = findViewById(R.id.pool_thing);
        textPool = findViewById(R.id.text_pool);
        try {
        textPool.setText(R.string.class.getField("q"+(i+1)).getInt(getResources()));
        } catch (Exception e){
        }
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.CENTER_HORIZONTAL;
        int USERID1 = 8000;
        int countID = 0;
        String numb = Integer.toString(i+1);
//        Toast.makeText(getApplicationContext(),
//                            set.toString(), Toast.LENGTH_SHORT).show();
        for(int i = 0; i < max; i++){
            if(set.charAt(i) == '1')
            {
                int i1=i+1;
                final ImageButton btnNew = new ImageButton(this);
                try {
                    btnNew.setImageResource(R.drawable.class.getField("i"+numb+(i1)).getInt(getResources()));
                } catch (Exception e){
//                    Toast.makeText(getApplicationContext(),
//                            numb, Toast.LENGTH_SHORT).show();
                }
                btnNew.setId(USERID1 + i);
                btnNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        respondSet.setCharAt((Integer)btnNew.getId()-8000,'1');
                        Intent intent = new Intent();
                        intent.putExtra("answer",respondSet.toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                pool_thing.addView(btnNew, lParams);
                final TextView respondItem = new TextView(this);
                try {
                    respondItem.setText(R.string.class.getField("a"+numb+(i1)).getInt(getResources()));
                } catch (Exception e){
                }
                pool_thing.addView(respondItem, lParams);
            }
        }

//        for(String str:answers)
//        {
//            final Button btnNew = new Button(this);
//            btnNew.setText(str);
//            btnNew.setId(USERID + countID++);
//            btnNew.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.putExtra("answer",btnNew.getText());
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }
//            });
//          pool_thing.addView(btnNew, lParams);
//        }
        final Button btnNew = new Button(this);
        btnNew.setText("Не могу определить");
        btnNew.setId(USERID1);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),
//                        "Не могу определить" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("answer","");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        pool_thing.addView(btnNew, lParams);
    }


}

