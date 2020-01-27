package crux.bphc.cms.Teacher;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crux.bphc.cms.R;

public class AnnouncementActivity extends AppCompatActivity {


    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;

    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    final private String RECYCLER_VIEW = "FIREBASE RECYCLER VIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Announcements");

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAnnouncements.setLayoutManager(linearLayoutManager);
        rvAnnouncements.setHasFixedSize(true);

        fetch();

        firebaseRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
//        firebaseRecyclerAdapter.stopListening();
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference("cms/announcements").child("TEST 1");
        FirebaseRecyclerOptions<AnnouncementModel> options = new FirebaseRecyclerOptions.Builder<AnnouncementModel>()
                .setQuery(query, snapshot -> new AnnouncementModel(
                        snapshot.child("announcementText").getValue().toString(),
                        snapshot.child("timestamp").getValue().toString()
                ))
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AnnouncementModel, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull AnnouncementModel model) {
                viewHolder.setAnnouncementText(model.getAnnouncementText());
                viewHolder.setDateText(model.getTimestamp());
                viewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "AWSM", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_announcement, parent, false);
                return new ViewHolder(v);

            }
        };
        rvAnnouncements.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }


    @OnClick(R.id.fabAdd)
    public void onViewClicked() {
        Toast.makeText(getApplicationContext(), "New Announcement", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), NewAnnouncement.class));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView tvAnnouncement;
        public TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.llRoot);
            tvAnnouncement = itemView.findViewById(R.id.tvAnnouncement);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void setAnnouncementText(String s) {
            tvAnnouncement.setText(s);
        }

        public void setDateText(String s) {
            tvDate.setText(s);
        }
    }
}
