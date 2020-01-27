package crux.bphc.cms.Teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crux.bphc.cms.R;

public class NewAnnouncement extends AppCompatActivity {

    static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.etText)
    EditText etText;
    @BindView(R.id.btUpload)
    Button btUpload;

    String subject = null;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cms/announcements");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_announcement);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("New Announcement");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(getApplicationContext(), subject, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subject = adapterView.getItemAtPosition(0).toString();
            }
        });
    }

    @OnClick(R.id.btUpload)
    public void onViewClicked() {
        uploadAnnouncement();
    }

    private void uploadAnnouncement() {
        if (etText.getText().toString().trim() != null) {
            AnnouncementModel announcement = new AnnouncementModel(
                    etText.getText().toString().trim(),
                    sdf.format(new Date()));
            ref.child(subject)
                    .child(ref.push().getKey())
                    .setValue(announcement);
            etText.setText("");
            Toast.makeText(getApplicationContext(), "Announcement Uploaded Successfully!", Toast.LENGTH_LONG).show();

        } else if (etText.getText().toString().trim() == null) {
            Toast.makeText(getApplicationContext(), "Announcement text cannot be null!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Announcement couldn't be uploaded!", Toast.LENGTH_LONG).show();
        }
    }
}
