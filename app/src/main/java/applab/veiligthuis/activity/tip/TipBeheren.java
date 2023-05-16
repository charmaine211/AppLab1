package applab.veiligthuis.activity.tip;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import applab.veiligthuis.MainActivity;
import applab.veiligthuis.R;
import applab.veiligthuis.activity.SignInUp.LogInActivity;
import applab.veiligthuis.model.tipsmodel.Tip;


public class TipBeheren extends AppCompatActivity {

    private ToggleButton toggleButton;

    private Button addButton;
    private boolean showDeleted = false;
    private DatabaseReference mDatabase;
    private ListView tipListView;
    private List<Tip> tipList;
    private TipListBeheerAdapter tipListAdapter;
    private FirebaseAuth mAuth;



    private void initAddButton() {
        addButton = findViewById(R.id.addTipButton);
        addButton.setVisibility(showDeleted ? View.GONE : View.VISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipBeheren.this, TipMaken.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_beheren);

        mAuth = FirebaseAuth.getInstance();

        initLogoClickEventHandler();

        mDatabase = FirebaseDatabase.getInstance().getReference("tips");
        initToggleButton();
        initAddButton();
        initTipListView();
        populateTipListView();
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null || currentUser.isAnonymous()) {
            Log.d(MotionEffect.TAG, "user is not logged in");

            Intent intent = new Intent(TipBeheren.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initToggleButton() {
        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showDeleted = isChecked;
                addButton.setVisibility(showDeleted ? View.GONE : View.VISIBLE);
                populateTipListView();
            }
        });
    }

    private void initTipListView() {
        tipListView = findViewById(R.id.tipListView);
        tipList = new ArrayList<>();
        tipListAdapter = new TipListBeheerAdapter(this, tipList);
        tipListView.setAdapter(tipListAdapter);

        tipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tip selectedTip = (Tip) parent.getItemAtPosition(position);

                //serialiseren omdat alleen primitives als parameter gebruikt kunnen worden parameter voor intent
                String tipJson = new Gson().toJson(selectedTip);
                Intent intent = new Intent(TipBeheren.this, TipDetailActivity.class);
                intent.putExtra("tipJson", tipJson);
                intent.putExtra("beheren", true);
                startActivity(intent);
            }
        });
    }

    private void populateTipListView() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipList.clear();

                for (DataSnapshot tipSnapshot : dataSnapshot.getChildren()) {
                    Tip tip = tipSnapshot.getValue(Tip.class);
                    if (showDeleted && tip.isVerwijderd()) {
                        tipList.add(tip);
                    } else if (!showDeleted && !tip.isVerwijderd()) {
                        tipList.add(tip);
                    }
                }

                tipListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void initLogoClickEventHandler() {
        ImageView logo = findViewById(R.id.image_view);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
