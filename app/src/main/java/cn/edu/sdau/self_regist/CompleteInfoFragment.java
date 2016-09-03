package cn.edu.sdau.self_regist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.test.suitebuilder.TestMethod;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import beans.StCompleteInfo;

/**
 * Created by brahm on 2016/7/5.
 */
public class CompleteInfoFragment extends Fragment {
    public static final String YES = "是";
    public static final String NO = "否";

    private EditText edittextPhoneNumber;
    private EditText edittextParentName;
    private EditText edittextParentPhoneNum;
    private EditText edittextHomeAddr;
    private EditText edittextShoeNum;
    private EditText edittextHeight;
    private EditText edittextWeight;

    private Switch switchBedding;
    private Switch switchMilitaryClothing;
    private Switch switchLoan;

    private Button buttonSubmit;

    private StCompleteInfo stCompleteInfo;

    private CompleteInfoTask completeInfoTask;
    private CheckCompleteInfoTask checkCompleteInfoTask;
    private boolean isRegist = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completeinfo, container, false);
        initViews(view);
        initEvents();
        checkCompleteInfoTask = new CheckCompleteInfoTask();
        checkCompleteInfoTask.execute((Void) null);
        return view;
    }

    private void initEvents() {
        switchBedding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    stCompleteInfo.setBedding(YES);
                } else {
                    stCompleteInfo.setBedding(NO);
                }
            }
        });

        switchMilitaryClothing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    stCompleteInfo.setMilitaryClothing(YES);
                } else {
                    stCompleteInfo.setMilitaryClothing(NO);
                }
            }
        });

        switchLoan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    stCompleteInfo.setLoan(YES);
                } else {
                    stCompleteInfo.setLoan(NO);
                }
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                beginSubmit();

            }
        });
    }

    private void initViews(View view) {
        edittextPhoneNumber = (EditText) view.findViewById(R.id.et_phoneNum);
        edittextParentName = (EditText) view.findViewById(R.id.et_parentName);
        edittextParentPhoneNum = (EditText) view.findViewById(R.id.et_parentPhoneNum);
        edittextHomeAddr = (EditText) view.findViewById(R.id.et_homeAddr);
        edittextShoeNum = (EditText) view.findViewById(R.id.et_shoeNum);
        edittextHeight = (EditText) view.findViewById(R.id.et_height);
        edittextWeight = (EditText) view.findViewById(R.id.et_weight);

        switchBedding = (Switch) view.findViewById(R.id.sw_bedding);
        switchBedding.setChecked(true);
        switchMilitaryClothing = (Switch) view.findViewById(R.id.sw_militaryClothing);
        switchMilitaryClothing.setChecked(true);
        switchLoan = (Switch) view.findViewById(R.id.sw_loan);
        switchLoan.setChecked(false);

        stCompleteInfo = new StCompleteInfo();
        stCompleteInfo.setBedding(YES);
        stCompleteInfo.setMilitaryClothing(YES);
        stCompleteInfo.setLoan(NO);
        buttonSubmit = (Button) view.findViewById(R.id.button_submit);

    }

    private void beginSubmit() {
        if (completeInfoTask != null) {
            return;
        }

        stCompleteInfo.setPhoneNum(edittextPhoneNumber.getText().toString());

        stCompleteInfo.setParentPhoneNum(edittextParentPhoneNum.getText().toString());
        stCompleteInfo.setHomeAddr(edittextHomeAddr.getText().toString());
        stCompleteInfo.setParentName(edittextParentName.getText().toString());

        edittextPhoneNumber.setError(null);
        edittextHeight.setError(null);
        edittextWeight.setError(null);
        edittextShoeNum.setError(null);
        edittextParentPhoneNum.setError(null);
        edittextHomeAddr.setError(null);
        edittextParentName.setError(null);

        String shoeNum = edittextShoeNum.getText().toString();
        String weight = edittextWeight.getText().toString();
        String height = edittextHeight.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(stCompleteInfo.getPhoneNum())) {
            edittextPhoneNumber.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = edittextPhoneNumber;
        }else if(!TextUtils.isDigitsOnly(stCompleteInfo.getPhoneNum())){
            edittextPhoneNumber.setError(getString(R.string.error_not_digtal));
            cancel = true;
            focusView = edittextPhoneNumber;
        }

        else if(TextUtils.isEmpty(stCompleteInfo.getHomeAddr())){
            edittextHomeAddr.setError(getString(R.string.error_field_required));
            cancel = true;

            focusView = edittextHomeAddr;
        }else if(TextUtils.isEmpty(stCompleteInfo.getParentName())){
            edittextParentName.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = edittextParentName;
        }else if(TextUtils.isEmpty(stCompleteInfo.getParentPhoneNum())){
            edittextParentPhoneNum.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = edittextParentPhoneNum;
        }else if(TextUtils.isEmpty(shoeNum)){
            edittextShoeNum.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = edittextShoeNum;
        }else if(!TextUtils.isDigitsOnly(shoeNum)){
            edittextShoeNum.setError(getString(R.string.error_not_digtal));
            cancel = true;
            focusView = edittextShoeNum;
        }else if(TextUtils.isEmpty(weight)){
            edittextWeight.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = edittextWeight;
        }else if(!TextUtils.isDigitsOnly(weight)){
            edittextWeight.setError(getString(R.string.error_not_digtal));
            cancel = true;
            focusView = edittextWeight;
        }else if(TextUtils.isEmpty(height)){
            edittextHeight.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = edittextHeight;
        }else if(!TextUtils.isDigitsOnly(height)){
            edittextHeight.setError(getString(R.string.error_not_digtal));
            cancel = true;
            focusView = edittextHeight;
        }



        if(cancel){
            focusView.requestFocus();
        }else{
            stCompleteInfo.setHeight(Integer.parseInt(edittextHeight.getText().toString()+""));
            stCompleteInfo.setWeight((Integer.parseInt(edittextWeight.getText().toString()+"")));
            stCompleteInfo.setShoeNum((Integer.parseInt(edittextShoeNum.getText().toString()+"")));
            Log.i("CompleteInfoFragment", stCompleteInfo.toString());

            completeInfoTask = new CompleteInfoTask(stCompleteInfo);
            completeInfoTask.execute(isRegist);
        }
    }

    protected class CompleteInfoTask extends AsyncTask<Boolean, Void, Boolean> {
        private StCompleteInfo stCompleteInfo;

        CompleteInfoTask(StCompleteInfo stCompleteInfo) {
            this.stCompleteInfo = stCompleteInfo;
        }


        protected Boolean doInBackground(Boolean... isRegist) {

            String url;
            if (isRegist[0]) {
                url = getString(R.string.url) + "student/dcstudentinfo!modifyStudentInfo";
            } else {
                url = getString(R.string.url) + "student/dcstudentinfo!completeStudentInfo";
            }

            RequestBody formBody = new FormEncodingBuilder().add("stCompleteInfo.bedding", stCompleteInfo.getBedding())
                    .add("sno", StepsActivty.getUser().getSno())
                    .add("stCompleteInfo.phoneNum", stCompleteInfo.getPhoneNum())
                    .add("stCompleteInfo.parentName", stCompleteInfo.getParentName())
                    .add("stCompleteInfo.parentPhoneNum", stCompleteInfo.getParentPhoneNum())
                    .add("stCompleteInfo.homeAddr", stCompleteInfo.getHomeAddr())
                    .add("stCompleteInfo.militaryClothing", stCompleteInfo.getMilitaryClothing())
                    .add("stCompleteInfo.shoeNum", stCompleteInfo.getShoeNum() + "")
                    .add("stCompleteInfo.height", stCompleteInfo.getHeight() + "")
                    .add("stCompleteInfo.weight", stCompleteInfo.getWeight() + "")
                    .add("stCompleteInfo.loan", stCompleteInfo.getLoan())
                    .build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            try {
                Response response = OkHttpUtil.execute(request);
                if (response.isSuccessful()) {
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
//            String url;
//            if (isRegist[0]) {
//                url = getString(R.string.url) + "student/dcstudentinfo!modifyStudentInfo?sno=" + StepsActivty.getUser().getSno() + "&";
//            } else {
//                url = getString(R.string.url) + "student/dcstudentinfo!completeStudentInfo?sno=" + StepsActivty.getUser().getSno() + "&";
//            }
//            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
//            params.add(new BasicNameValuePair("stCompleteInfo.bedding", stCompleteInfo.getBedding()));
//            params.add(new BasicNameValuePair("stCompleteInfo.phoneNum", stCompleteInfo.getPhoneNum()));
//            params.add(new BasicNameValuePair("stCompleteInfo.parentName", stCompleteInfo.getParentName()));
//            params.add(new BasicNameValuePair("stCompleteInfo.parentPhoneNum", stCompleteInfo.getParentPhoneNum()));
//            params.add(new BasicNameValuePair("stCompleteInfo.homeAddr", stCompleteInfo.getHomeAddr()));
//            params.add(new BasicNameValuePair("stCompleteInfo.militaryClothing", stCompleteInfo.getMilitaryClothing()));
//            params.add(new BasicNameValuePair("stCompleteInfo.shoeNum", stCompleteInfo.getShoeNum() + ""));
//            params.add(new BasicNameValuePair("stCompleteInfo.height", stCompleteInfo.getHeight() + ""));
//            params.add(new BasicNameValuePair("stCompleteInfo.weight", stCompleteInfo.getWeight() + ""));
//            params.add(new BasicNameValuePair("stCompleteInfo.loan", stCompleteInfo.getLoan()));
//
//            url += URLEncodedUtils.format(params, "UTF-8");
//
//            Log.i("CompleteInfoFragment", url);
//
//            try {
//                String response = OkHttpUtil.getStringFromServer(url);
//                Log.i("CompleteInfoFragment", "response:" + response);
//                JSONObject jsonObject = new JSONObject(response);
//                boolean isSuccess = jsonObject.getBoolean("success");
//                return isSuccess;
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                isRegist = true;
                showMsg("提交成功");
            } else {
                showMsg("出现错误，请重新尝试");
            }
            completeInfoTask = null;
        }
    }

    public class CheckCompleteInfoTask extends AsyncTask<Void, Void, StCompleteInfo> {
        @Override
        protected StCompleteInfo doInBackground(Void... voids) {
            try {
                String response = OkHttpUtil.getStringFromServer(getString(R.string.url) + "student/dcstudentinfo!checkStCompleteInfo?sno=" + StepsActivty.getUser().getSno());
                JSONObject jsonObject = new JSONObject(response);

                Log.i("CheckCompleteInfoTask", response);
                boolean message = jsonObject.getBoolean("message");
                if (message == false) {
                    return null;
                } else {
                    JSONObject stInfo = jsonObject.getJSONObject("stcompleteInfo");
                    StCompleteInfo stComInfo = new StCompleteInfo();
                    stComInfo.setBedding(stInfo.getString("bedding"));
                    stComInfo.setLoan(stInfo.getString("loan"));
                    stComInfo.setHomeAddr(stInfo.getString("homeAddr"));
                    stComInfo.setMilitaryClothing(stInfo.getString("militaryClothing"));
                    stComInfo.setParentName(stInfo.getString("parentName"));
                    stComInfo.setParentPhoneNum(stInfo.getString("parentPhoneNum"));
                    stComInfo.setPhoneNum(stInfo.getString("phoneNum"));
                    stComInfo.setHeight(stInfo.getInt("height"));
                    stComInfo.setShoeNum(stInfo.getInt("shoeNum"));
                    stComInfo.setWeight(stInfo.getInt("weight"));

                    return stComInfo;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(StCompleteInfo stCompleteInfo) {
            if (null == stCompleteInfo) {
                isRegist = false;
                return;
            } else {
                isRegist = true;
                edittextPhoneNumber.setText(stCompleteInfo.getPhoneNum());
                edittextParentName.setText(stCompleteInfo.getParentName());
                edittextParentPhoneNum.setText(stCompleteInfo.getParentPhoneNum());
                edittextHomeAddr.setText(stCompleteInfo.getHomeAddr());
                edittextShoeNum.setText(stCompleteInfo.getShoeNum() + "");
                edittextWeight.setText(stCompleteInfo.getWeight() + "");
                edittextHeight.setText(stCompleteInfo.getHeight() + "");
                switchBedding.setChecked((stCompleteInfo.getBedding().equals(YES)));
                switchLoan.setChecked(stCompleteInfo.getLoan().equals(YES));
                switchMilitaryClothing.setChecked(stCompleteInfo.getMilitaryClothing().equals(YES));
            }
        }
    }

    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}

