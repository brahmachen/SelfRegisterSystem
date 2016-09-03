package cn.edu.sdau.self_regist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import beans.BasicInfo;

/**
 * Created by brahm on 2016/7/5.
 */
public class BasicInfoFragment extends Fragment {

    private EditText editTextSno;
    private EditText editTextName;
    private EditText editTextCollege;
    private EditText editTextSex;
    private EditText editTextExamNum;
    private EditText editTextIdNum;
    private EditText editTextMajor;
    private EditText editTextClassNum;
    private EditText editTextCompus;
    private EditText editTextDormNum;
    private EditText editTextPaySta;
    private EditText editTextCollegeTel;
    private EditText editTextSchoolTel;

    private ImageView imageViewHead;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basicinfo,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        Log.i("BasicInfoFragment","initViews");
        editTextSno = (EditText)view.findViewById(R.id.et_sno);
        editTextName = (EditText)view.findViewById(R.id.et_name);
        editTextCollege = (EditText)view.findViewById(R.id.et_college);
        editTextSex = (EditText)view.findViewById(R.id.et_sex);
        editTextExamNum = (EditText)view.findViewById(R.id.et_exam_num);
        editTextIdNum = (EditText)view.findViewById(R.id.et_id_num);
        editTextMajor = (EditText)view.findViewById(R.id.et_major);
        editTextClassNum = (EditText)view.findViewById(R.id.et_class_num);
        editTextCompus = (EditText)view.findViewById(R.id.et_campus);
        editTextDormNum = (EditText)view.findViewById(R.id.et_dorm_num);
        editTextPaySta = (EditText)view.findViewById(R.id.et_pay_sta);
        editTextCollegeTel = (EditText)view.findViewById(R.id.et_college_tel);
        editTextSchoolTel = (EditText)view.findViewById(R.id.et_school_tel);

        imageViewHead = (ImageView) view.findViewById(R.id.iv_head);
        BasicInfoTask basicInfoTask = new BasicInfoTask();
        basicInfoTask.execute();
    }

    public void setData(BasicInfo basicInfo){
        if(basicInfo==null){
            return;
        }
        editTextSno.setText(basicInfo.getSno());
        editTextName.setText(basicInfo.getName());
        editTextCollege.setText(basicInfo.getCollege());
        editTextSex.setText(basicInfo.getSex());
        editTextExamNum.setText(basicInfo.getExamNum());
        editTextIdNum.setText(basicInfo.getIDNum());
        editTextMajor.setText(basicInfo.getMajor());
        editTextClassNum.setText(basicInfo.getClassNum());
        editTextCompus.setText(basicInfo.getCampus());
        editTextDormNum.setText(basicInfo.getDormNum());
        editTextPaySta.setText(basicInfo.getPaySta());
        editTextCollegeTel.setText(basicInfo.getCollegeTel());
        editTextSchoolTel.setText(basicInfo.getSchoolTel());
    }


    public class BasicInfoTask extends AsyncTask<Void,Void, BasicInfo> {
        @Override
        protected BasicInfo doInBackground(Void... voids) {
            try{
                String response = OkHttpUtil.getStringFromServer(getString(R.string.url)+"student/dbasicinfo?sno="+StepsActivty.getUser().getSno());
                JSONObject jsonObject = new JSONObject(response);
                Log.i("BasicInfoFragment",response);
                boolean success = jsonObject.getBoolean("success");
                if(success){
                    /*

                    {"basicInfo":{"IDNum":"321034123456130028",
                    "campus":"高新A区","classNum":"1","college":"理学院",
                    "collegeTel":"12345678901",
                    "dormNum":"A区B号楼101房间01床",
                    "examNum":"15372901151473","major":"密码学",
                    "name":"张三",
                    "paySta":"应扣款3360元，扣款结果：成功",
                    "schoolTel":"12345678901","sex":"女",
                    "sno":"20150001","stuPhoto":null},"success":true}

                     */
                    JSONObject jsonBasicInfo = jsonObject.getJSONObject("basicInfo");
                    BasicInfo basicInfo = new BasicInfo();
                    basicInfo.setIDNum(jsonBasicInfo.getString("IDNum"));
                    basicInfo.setCampus(jsonBasicInfo.getString("campus"));
                    basicInfo.setClassNum(jsonBasicInfo.getString("classNum"));
                    basicInfo.setCollege(jsonBasicInfo.getString("college"));
                    basicInfo.setCollegeTel(jsonBasicInfo.getString("collegeTel"));
                    basicInfo.setDormNum(jsonBasicInfo.getString("dormNum"));
                    basicInfo.setExamNum(jsonBasicInfo.getString("examNum"));
                    basicInfo.setMajor(jsonBasicInfo.getString("major"));
                    basicInfo.setName(jsonBasicInfo.getString("name"));
                    basicInfo.setPaySta(jsonBasicInfo.getString("paySta"));
                    basicInfo.setSchoolTel(jsonBasicInfo.getString("schoolTel"));
                    basicInfo.setSno(jsonBasicInfo.getString("sno"));
                    basicInfo.setSex(jsonBasicInfo.getString("sex"));
                    return basicInfo;
                }else{
                    return null;
                }
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(BasicInfo basicInfo) {
            if(null!=basicInfo){
                Log.i("StepsActivity",basicInfo.toString());
                setData(basicInfo);
                StepsActivty.setBasicInfo(basicInfo);
                HeadPictureTask headPictureTask = new HeadPictureTask(basicInfo.getExamNum());
                headPictureTask.execute();
            }
        }
    }
    public class HeadPictureTask extends AsyncTask<Void,Void,Bitmap>{

        private String examNum;

        HeadPictureTask(String examNum){
            this.examNum = examNum;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String url = getString(R.string.url)+"images/photo/"+examNum+".jpg";
            try {
                Log.i("头像url",url);
                Response response = OkHttpUtil.execute(new Request.Builder().url(url).build());
                InputStream is = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap==null){

            }else{
                imageViewHead.setImageBitmap(bitmap);
            }
        }
    }
}
