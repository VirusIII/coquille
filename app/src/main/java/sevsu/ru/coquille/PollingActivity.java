package sevsu.ru.coquille;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollingActivity extends AppCompatActivity {
    Map<String, String[]> tags, firstTags, secondTags, currentTags;
    Map<String, Integer> answerList;
    List<Integer> useless;
    LinearLayout polling;
    String[] respond, key, helpMePlease, respondDup;
    String[][] respondId;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);
        polling = findViewById(R.id.poling);
        respond = new String[53];
        respondId = new String[2][53];
        respondDup = new String[53];
        useless = new ArrayList<>();
        tags = readTags();
        firstTags = new HashMap<>(tags);
        secondTags = new HashMap<>(tags);
        currentTags = new HashMap<>(tags);
        answerList = new HashMap<>();
        setUseless(tags);
        i = selectQuestion(tags, useless);
        for (Map.Entry entry : tags.entrySet()) {
            key = (String[]) entry.getValue();
            if (answerList.get(key[i]) == null) {
                answerList.put(key[i], 0);
            }
        }
        helpMePlease = new String[answerList.size()];
        int j = 0;
        for (Map.Entry entry : answerList.entrySet()) {
            helpMePlease[j] = (String) entry.getKey();
            j++;
        }
        Intent intent = new Intent(this, PoolThingActivity.class);
        intent.putExtra("answers", helpMePlease);
        intent.putExtra("number", i);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String answer = (String) data.getStringExtra("answer");

        useless.add(i);
        if (answer.length()!=0) {
            respondDup[i] = answer;
            if (firstTags.size() > 1) {
                respondId[0][i] = answer;
                firstTags = selectTagsId(firstTags, i, respondId[0]);
                currentTags = firstTags.size() > 1 ? firstTags : secondTags;
//                Toast.makeText(getApplicationContext(),
//                        firstTags.size()+"FT", Toast.LENGTH_SHORT).show();
            } else if (secondTags.size() > 1) {
                respondId[1][i] = answer;
                secondTags = selectTagsId(secondTags, i, respondId[1]);
                currentTags = secondTags.size() > 1 ? secondTags : firstTags;
//                Toast.makeText(getApplicationContext(),
//                        secondTags.size()+"ST", Toast.LENGTH_SHORT).show();
            }
            if (firstTags.size() == 1 && secondTags.size() == 1) {
                for (Map.Entry entry : firstTags.entrySet()) {
                    for (Map.Entry entr : secondTags.entrySet()) {
                        if (entry.getKey().equals(entr.getKey())) {
                            setButtons(firstTags);
                            return;
                        } else {

                            String[] firstTag = (String[]) entry.getValue();
                            String[] secondTag = (String[]) entr.getValue();
                            for (int j = 0; j < 53; j++) {
                                respond[j] = respondId[0][j] == null ? respondId[1][j] : respondId[0][j];
                                if (respond[j]!=null){
                                    for(int k=0;k<respond[j].length();k++){
                                        if(respond[j].charAt(k)=='1'){

                                            if(!(firstTag[j].equals("*"))&& firstTag[j].length()<=1){
                                                respond[j] = null;
                                                break;
                                            }else if ((firstTag[j].length()>1) && firstTag[j].charAt(k)!='1'){
                                                respond[j] = null;
                                                break;
                                            }
                                            if(!(secondTag[j].equals("*"))&& secondTag[j].length()<=1){
                                                respond[j] = null;
                                                break;
                                            }else if ((secondTag[j].length()>1) && secondTag[j].charAt(k)!='1'){
                                                respond[j] = null;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            firstTags = new HashMap<>(selectTags(tags));
                            currentTags = firstTags;
                            if (useless.size() != 53) {
                                secondTags = new HashMap<>(firstTags);
                            } else {
                                return;
                            }
                        }
                    }
                }
            }
        }

        i = selectQuestion(currentTags, useless);
//        Toast.makeText(getApplicationContext(),
//                        "I"+i, Toast.LENGTH_SHORT).show();
            if(i==-1){
                answerList = new HashMap<>();
                Map<String,Integer> rating =new HashMap<>();
                int max = 0;
                int count = 0;

                for (Map.Entry entry:tags.entrySet()){
                    String[] set =(String[])entry.getValue();
                    for (int i = 0;i<53;i++){
                        if(respondDup[i]!=null){
                            if (set[i].equals("*")) {
                                count++;
                                continue;
                            }
                            if (set[i].equals("0")) {
                                continue;
                            }
                            for(int j = 0;j<respondDup[i].length();j++){
                                if(respondDup[i].charAt(j)=='1' && set[i].charAt(j)=='1'){
                                    count++;
                                }
                            }
                        }
                    }
                    max = count>max?count:max;
                    rating.put((String)entry.getKey(),count);
                    count = 0;
                }
                Toast.makeText(getApplicationContext(),max+"", Toast.LENGTH_SHORT).show();
                Map<String, String[]> tmp = new HashMap<>();
                for (Map.Entry entry:rating.entrySet()){
                    if((Integer)entry.getValue()==max){
                        tmp.put((String)entry.getKey(),tags.get((String)entry.getKey()));
                    }
                }
                setButtons(tmp);
            } else {
                answerList = new HashMap<>();
                for (Map.Entry entry : currentTags.entrySet()) {
                    key = (String[]) entry.getValue();
                    if (answerList.get(key[i]) == null) {
                        answerList.put(key[i], 0);
                    }
                }
                helpMePlease = new String[answerList.size()];
                int j = 0;
                for (Map.Entry entry : answerList.entrySet()) {
                    helpMePlease[j] = (String) entry.getKey();
                    j++;
//                    Toast.makeText(getApplicationContext(),(String) entry.getKey(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, PoolThingActivity.class);
                intent.putExtra("answers", helpMePlease);
                intent.putExtra("number", i);
                startActivityForResult(intent, 1);
            }

    }

    void setButtons(Map<String, String[]> tags) {
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // переносим полученное значение выравнивания в LayoutParams
        lParams.gravity = Gravity.CENTER_HORIZONTAL;
        int USERID = 7000;
        int countID = 0;
        String allTypes = getString(R.string.all_types1) + getString(R.string.all_types2) + getString(R.string.all_types3);
        final String[] things = allTypes.split("@");
        for (int i = 0; i<things.length;i+=2) {
            if(tags.get(things[i])!=null) {
                final Button btnNew = new Button(this);
                btnNew.setText(things[i]);
                btnNew.setId(USERID + countID++);
                btnNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PollingActivity.this, DigestThingActivity.class);
                        intent.putExtra("sortName", (String) btnNew.getText());
                        intent.putExtra("sortSpecification", (String) things[((int)btnNew.getId()-7000)*2+1]);
                        startActivity(intent);
                    }
                });
                polling.addView(btnNew, lParams);
            }
        }
    }

    Map<String, String[]> readTags() {//разбирает строки данных
        Map<String, String[]> rTags = new HashMap<>();
        String[] sTags = getString(R.string.tag).split(";;");
        String[] thing;
        for (String str : sTags) {
            thing = str.split(";", 2);
            rTags.put(thing[0].trim(), thing[1].split(";"));
        }
        return rTags;
    }

    Map<String, String[]> selectTags(Map<String, String[]> poole) {//возвращает список видов, с характеристиками, заданными в respond
        Map<String, String[]> selectedTags = new HashMap<>();
        String[] str;
        boolean fl;
        for (Map.Entry entry : poole.entrySet()) {
            fl = true;
            str = (String[]) entry.getValue();
            for (int i = 0; i < 53; i++) {
                if (str[i].equals("*")){
                    continue;
                }
                if (str[i].equals("0")){
                    fl = false;
                    break;
                }
                if ((respond[i] != null) && str[i].length() == respond[i].length()) {
                    if(str[i].charAt(respond[i].indexOf('1'))!='1'){
                        fl = false;
                        break;
                    }
                }
            }
            if (fl) {
                selectedTags.put((String) entry.getKey(), (String[]) entry.getValue());
            }
        }
        return selectedTags;
    }

    Map<String, String[]> selectTagsId(Map<String, String[]> poole, int i, String[] respond) {//возвращает список видов, с заданным параметром i,
        Map<String, String[]> selectedIdTags = new HashMap<>();
        String[] str;
        for (Map.Entry entry : poole.entrySet()) {
            str = (String[]) entry.getValue();
            if (str[i].equals("*")){
                selectedIdTags.put((String) entry.getKey(), (String[]) entry.getValue());
            }
            if (str[i].equals("0")){
                continue;
            }
            if ((respond[i] != null) && str[i].length() == respond[i].length()) {
                if(str[i].charAt(respond[i].indexOf('1'))=='1'){
                    selectedIdTags.put((String) entry.getKey(), (String[]) entry.getValue());
                }
            }
        }
        return selectedIdTags;
    }

    int selectQuestion(Map<String, String[]> poole, List numbers) {
        //Для каждого вопроса определяется количество видов, соответствующих кажому ответу;
        List<Map<String, Integer>> qualityList = new ArrayList<>();//количество ответов на вопрос
        Map<String, Integer> quality;
        String[] key;
        int max = 0;
//        int maxL = 0;
        int maxScoreIndex = 0;
        for (int i = 0; i < 53; i++) {
            quality = new HashMap<>();
            if (!numbers.contains(i)) {
//                for (Map.Entry entry : poole.entrySet()) {//найти количество ответов
//                    key = (String[]) entry.getValue();
//                    if(key[i]!=null && key[i].length()>maxL){
//                        maxL=key[i].length();
//                    }
//                }
                for (Map.Entry entry : poole.entrySet()) {//найти количество ответов
                    key = (String[]) entry.getValue();
                    if(key[i]!=null && key[i].length()>1){
                        for (int j=0;j<key[i].length();j++){
                            if(key[i].charAt(j) == '1') {

                                if (quality.get("" + j) == null) {
                                    quality.put("" + j, 1);
                                } else {
                                    quality.put("" + j, quality.get("" + j) + 1);
                                }
                            }
                        }
                    }else if(key[i]!=null && key[i].equals("0")){
                        if (quality.get("7") == null) {
                            quality.put("7", 1);
                        } else {
                            quality.put("7", quality.get("7")+1);
                        }
                    }
                }


            }
            qualityList.add(quality);

            if (quality.size() > max) {
                max = quality.size();
                maxScoreIndex = i;
            }

        }
        if (max<2){
            return -1;
        }


        float maxScore = 100000;
        float nextScore = 0;
        float count = poole.size();
        count /= max;
        Map<String, Integer> question;
        for (int i = 0; i < 53; i++) {
            question = qualityList.get(i);
            if (question.size() == max) {
                for (Map.Entry entry : question.entrySet()) {
                    nextScore += Math.abs(count - (Integer) entry.getValue());
                }
                if (nextScore < maxScore) {
                    maxScore = nextScore;
                    maxScoreIndex = i;
                }
//                Toast.makeText(getApplicationContext(),
//                        maxScore+"MAX"+i, Toast.LENGTH_SHORT).show();
            }
            nextScore = 0;
        }

        return maxScoreIndex;
    }

    void setUseless(Map<String, String[]> poole) {//добавляет в список бесполезных признаков те, у которых единственное значение для всех видов.
        useless = new ArrayList<>();
        Map<String, Integer> tmp;
        String[] key;
        for (int i = 0; i < 53; i++) {
            tmp = new HashMap<>();
            for (Map.Entry entry : poole.entrySet()) {
                key = (String[]) entry.getValue();
                if (tmp.get(key[i]) == null) {
                    tmp.put(key[i], 1);
                } else {
                    tmp.put(key[i], tmp.get(key[i]) + 1);
                }
            }
            if (tmp.size() == 1) {
                useless.add(i);
            }
        }
    }
}
