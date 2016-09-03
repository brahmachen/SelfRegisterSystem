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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import beans.User;

/**
 * Created by brahm on 2016/7/5.
 */
public class ChangePwFragment extends Fragment {

    private EditText editTextOldPw;
    private EditText editTextNewPw;
    private EditText editTextNewRep;
    private View processChangePw;

    private ChangePwTask changePwTask = null;

    private Button buttonBeginChange;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepasswd, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        editTextOldPw = (EditText) view.findViewById(R.id.et_oldpassword);
        editTextNewPw = (EditText) view.findViewById(R.id.et_newpassword);
        editTextNewRep = (EditText) view.findViewById(R.id.et_repeatpassword);
        processChangePw = view.findViewById(R.id.changepw_progress);

        buttonBeginChange = (Button) view.findViewById(R.id.button_begin_change);
        buttonBeginChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                beginChange();
            }
        });
    }

    public void beginChange() {
        if (changePwTask != null)
            return;
        editTextNewRep.setError(null);
        editTextNewPw.setError(null);
        editTextOldPw.setError(null);

        String oldPasswd = editTextOldPw.getText().toString();
        String newPasswd = editTextNewPw.getText().toString();
        String newRep = editTextNewRep.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(oldPasswd)) {
            editTextOldPw.setError(getString(R.string.error_field_required));
            focusView = editTextOldPw;
            cancel = true;
        }else {
            if(!StepsActivty.getUser().getPassword().equals(oldPasswd)){
                editTextOldPw.setError(getString(R.string.error_incorrect_password));
                focusView = editTextOldPw;
                cancel = true;
            }
        }

        if (TextUtils.isEmpty(newPasswd)) {
            editTextNewPw.setError(getString(R.string.error_field_required));
            focusView = editTextNewPw;
            cancel = true;
        } else {
            if (newPasswd.length() < 4) {
                editTextNewPw.setError("密码太短");
                focusView = editTextNewPw;
                cancel = true;
            }
        }

        if (TextUtils.isEmpty(newRep)) {
            editTextNewRep.setError(getString(R.string.error_field_required));
            focusView = editTextNewRep;
            cancel = true;
        } else if (!newRep.equals(newPasswd)) {
            editTextNewRep.setError("两次输入不同，请重新输入");
            focusView = editTextNewRep;
            cancel = true;
        }

        if (!cancel) {
            Log.i("", "可以");
            showProgress(true);
            User user = StepsActivty.getUser();
            user.setNewpassword(newPasswd);
            changePwTask = new ChangePwTask(user);
            changePwTask.execute((Void)null);
        }else{
            focusView.requestFocus();
            showProgress(false);
        }

    }

    public void showProgress(boolean show){
        if(show){
            processChangePw.setVisibility(View.VISIBLE);
        }else{
            processChangePw.setVisibility(View.GONE);
        }
    }
    public class ChangePwTask extends AsyncTask<Void, Void, Boolean> {

        private final User user;

        ChangePwTask(User user) {
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String response = OkHttpUtil.getStringFromServer(getString(R.string.url) + "user/dlogin!motifyPwd?sno=" + user.getSno() + "&password=" + user.getPassword() + "&newpassword=" + user.getNewpassword());
                Log.i("ChangePwFragment", response);
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                if (success == true) {
                    return true;
                }
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {

            changePwTask = null;
            showProgress(false);
            if(success){
                showMsg("修改成功");
                user.setPassword(user.newpassword);
                user.setNewpassword("null");
                StepsActivty.setUser(user);
            }else{
                showMsg("出现错误，请重新尝试");
            }
        }
    }
    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
