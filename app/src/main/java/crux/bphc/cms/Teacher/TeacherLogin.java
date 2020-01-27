package crux.bphc.cms.Teacher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crux.bphc.cms.R;

public class TeacherLogin extends AppCompatActivity {

    @BindView(R.id.etuserName)
    EditText etuserName;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.btSignUp)
    Button btSignUp;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("TeacherPref", 0);
        if (pref.getString("name", null) != null) {
            startActivity(new Intent(getApplicationContext(), AnnouncementActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btSignUp)
    public void onViewClicked() {
        if (etuserName.getText().toString().trim().equals("dummy_faculty")
                && etPwd.getText().toString().equals("dummyFaculty1*")) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("name", etuserName.getText().toString().trim());
            editor.apply();
            Toast.makeText(getApplicationContext(),
                    "Signed In " + etuserName.getText().toString().trim(),
                    Toast.LENGTH_LONG).show();

            startActivity(new Intent(getApplicationContext(), AnnouncementActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Username or password incorrect!", Toast.LENGTH_LONG).show();
        }
    }
}
