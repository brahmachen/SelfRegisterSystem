package cn.edu.sdau.self_regist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import beans.TrafficInfo;

/**
 * Created by brahm on 2016/7/5.
 */
public class TrafficFragment extends Fragment {

    private Spinner spinnerTraffic;
    private ArrayAdapter<String> adapterTraffic;
    private static final String[] stringTraffic = {"自驾","汽车","火车","飞机"};
    private int selectPositionTraffic = 0;

    private Spinner spinnerTime;
    private ArrayAdapter<String> adapterTime;
    private static final String[] stringTime = {"2012-12-11下午","2012-12-12上午","2012-12-12下午"};

    private EditText editTextPeopleCount;
    private boolean isInsert = false;

    private CheckTrafficInfoTask checkTrafficInfoTask;
    private CompleteTrafficInfoTask completeTrafficInfoTask;

    private Button buttonSubmit;

    private TrafficInfo trafficInfo;

    private TextView textViewPeopleCount;
    private PeopleNumberTask peopleNumberTask;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic_way,container,false);
        initViews(view);
        initEvents();

        checkTrafficInfoTask = new CheckTrafficInfoTask();
        checkTrafficInfoTask.execute();
        return view ;
    }

    private void initEvents() {
        trafficInfo = new TrafficInfo();
        trafficInfo.setRegisterTime(stringTime[0]);
        trafficInfo.setTrafficWay(stringTraffic[0]);

        spinnerTraffic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trafficInfo.setTrafficWay(stringTraffic[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trafficInfo.setRegisterTime(stringTime[i]);
                if(peopleNumberTask==null){
                    peopleNumberTask = new PeopleNumberTask(stringTime[i]);
                    peopleNumberTask.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                beginSubmit();

            }
        });
    }

    private void beginSubmit() {
        if(completeTrafficInfoTask!=null){
            return;
        }
        editTextPeopleCount.setError(null);

        String peopleCount = editTextPeopleCount.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(peopleCount)) {
            editTextPeopleCount.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = editTextPeopleCount;
        }else if(!TextUtils.isDigitsOnly(peopleCount)){
            editTextPeopleCount.setError(getString(R.string.error_not_digtal));
            cancel = true;
            focusView = editTextPeopleCount;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            trafficInfo.setCompany(Integer.parseInt(peopleCount));

            completeTrafficInfoTask = new CompleteTrafficInfoTask(trafficInfo);
            completeTrafficInfoTask.execute(isInsert);
        }
    }

    private void initViews(View view) {

        spinnerTraffic = (Spinner) view.findViewById(R.id.sp_way);
        adapterTraffic = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,stringTraffic);
        adapterTraffic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraffic.setAdapter(adapterTraffic);

        spinnerTime = (Spinner) view.findViewById(R.id.sp_time);
        adapterTime = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,stringTime);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);

        editTextPeopleCount = (EditText) view.findViewById(R.id.et_people_count);

        buttonSubmit = (Button)view.findViewById(R.id.button_submit1);

        textViewPeopleCount = (TextView)view.findViewById(R.id.baodao_count);

    }
    public class CheckTrafficInfoTask extends AsyncTask<Void,Void, TrafficInfo>{

        @Override
        protected TrafficInfo doInBackground(Void... voids) {

            try {
                String response = OkHttpUtil.getStringFromServer(getString(R.string.url)+"student/traffic!checkTrafficInfo?sno="+StepsActivty.getUser().getSno());
                JSONObject jsonObject = new JSONObject(response);
                Log.i("CheckTrafficInfoTask",response);
                boolean message = jsonObject.getBoolean("message");
                if(message==true){
                    JSONObject trafficInfoJSON = jsonObject.getJSONObject("trafficInfo");
                    TrafficInfo trafficInfo = new TrafficInfo();
                    trafficInfo.setTrafficWay(trafficInfoJSON.getString("trafficWay"));
                    trafficInfo.setCompany(trafficInfoJSON.getInt("company"));
                    trafficInfo.setRegisterTime(trafficInfoJSON.getString("registerTime"));
                    trafficInfo.setCampus(trafficInfoJSON.getString("campus"));

                    JSONObject registerDateJSON = jsonObject.getJSONObject("registerDate");
                    trafficInfo.setMaxDate(registerDateJSON.getString("maxDate"));
                    trafficInfo.setRegisterDate1(registerDateJSON.getString("registerDate1"));
                    trafficInfo.setRegisterDate2(registerDateJSON.getString("registerDate2"));
                    trafficInfo.setRegisterDate3(registerDateJSON.getString("registerDate3"));

                    return trafficInfo;
                }else return null;
            } catch (IOException e) {;
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TrafficInfo trafficInfo) {
            if(trafficInfo==null){
                isInsert = false;
                return;
            }else{
                isInsert = true;

                stringTime[0] = trafficInfo.getRegisterDate1();
                stringTime[1] = trafficInfo.getRegisterDate2();
                stringTime[2] = trafficInfo.getRegisterDate3();

                switch (trafficInfo.getTrafficWay()){
                    case "自驾":
                        selectPositionTraffic = 0;
                        break;
                    case "汽车":
                        selectPositionTraffic = 1;
                        break;
                    case "火车":
                        selectPositionTraffic = 2;
                        break;
                    case "飞机":
                        selectPositionTraffic = 3;
                        break;
                    default:
                        break;
                }
                spinnerTraffic.setSelection(selectPositionTraffic);

                for(int i = 0;i<stringTime.length;i++){
                    if(stringTime[i].equals(trafficInfo.getRegisterTime())){
                        spinnerTime.setSelection(i);
                        break;
                    }
                }

                editTextPeopleCount.setText(trafficInfo.getCompany()+"");

            }
        }
    }

    public class CompleteTrafficInfoTask extends AsyncTask<Boolean,Void,Boolean>{

        private TrafficInfo trafficInfo;

        CompleteTrafficInfoTask(TrafficInfo trafficInfo){
            this.trafficInfo = trafficInfo;
        }
        @Override
        protected Boolean doInBackground(Boolean... isInsert) {
            String url;
            if(!isInsert[0]){
                url = getString(R.string.url)+"student/traffic!completeTrafficInfo";
            }else{
                url = getString(R.string.url)+"student/traffic!modifyTrafficInfo";
            }

            RequestBody formBody = new FormEncodingBuilder()
                    .add("sno", StepsActivty.getUser().getSno())
                    .add("trafficInfo.trafficWay",trafficInfo.getTrafficWay())
                    .add("trafficInfo.company",trafficInfo.getCompany()+"")
                    .add("trafficInfo.registerTime",trafficInfo.getRegisterTime())
                    .build();
            Request request = new Request.Builder().url(url).post(formBody).build();

            try {
                Response response = OkHttpUtil.execute(request);
                if(response.isSuccessful()){
                    String responseUrl = response.body().string();
                    Log.i("responseUrl",responseUrl);

                    JSONObject jsonObject = new JSONObject(responseUrl);
                    boolean isSuccess = jsonObject.getBoolean("success");
                    return isSuccess;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean==true){
                isInsert = true;
                showMsg("提交成功");
            }else{
                showMsg("出现错误，请重新尝试");
            }
            completeTrafficInfoTask = null;
        }
    }

    private void showMsg(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public class PeopleNumberTask extends AsyncTask<Void,Void,Integer>{

        private String registerTime;

        PeopleNumberTask(String registerTime){
            this.registerTime = registerTime;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            String url = getString(R.string.url)+"student/traffic!countRegisterTimeNum";
            RequestBody formBody = new FormEncodingBuilder()
                    .add("sno", StepsActivty.getUser().getSno())
                    .add("trafficInfo.registerTime",registerTime)
                    .add("trafficInfo.campus",StepsActivty.getBasicInfo().getCampus())
                    .build();
            Request request = new Request.Builder().url(url).post(formBody).build();

            try {
                Response response = OkHttpUtil.execute(request);
                if(response.isSuccessful()){
                    String responseUrl = response.body().string();
                    Log.i("responseUrl",responseUrl);

                    JSONObject jsonObject = new JSONObject(responseUrl);
                    boolean isSuccess = jsonObject.getBoolean("success");
                    if(isSuccess){
                        int countNum = jsonObject.getInt("countNum");
                        return countNum;
                    }return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (null != integer) {
                textViewPeopleCount.setText("该时段预计报到人数：" + integer);
            } else {
                textViewPeopleCount.setText("该时段预计报到人数：出现错误");
            }
            peopleNumberTask = null;
        }
    }
}
