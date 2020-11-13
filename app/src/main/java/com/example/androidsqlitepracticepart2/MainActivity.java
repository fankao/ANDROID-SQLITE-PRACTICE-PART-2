package com.example.androidsqlitepracticepart2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtLocation;
    Button btnSave, btnCancel;
    RecyclerView recyclerLocation;
    LocationAdapter adapter;
    List<Location> locations;
    DBManager dbManager;
    private Location locationSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerLocation = findViewById(R.id.recycler_location);
        recyclerLocation.setLayoutManager(new LinearLayoutManager(this));
        dbManager = new DBManager(this);
        getListLocations();
        adapter = new LocationAdapter(this, locations);
        recyclerLocation.setAdapter(adapter);
        adapter.setItemClickListener(new LocationAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (view.getId()) {
                    case R.id.button_edit:
                        edtLocation.setText(locations.get(pos).getName());
                        locationSelected = locations.get(pos);
                        break;
                    case R.id.button_delete:
                        deleteLocation(locations.get(pos));
                        break;
                }
            }
        });

        edtLocation = findViewById(R.id.input_location);
        edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput(edtLocation.getText().toString());

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput(edtLocation.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkInput(edtLocation.getText().toString());
            }
        });

        btnSave = findViewById(R.id.button_save);
        checkInput(edtLocation.getText().toString());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtLocation.getText().toString())) return;
                String locationName = edtLocation.getText().toString();
                if (locationSelected != null) {
                    locationSelected.setName(locationName);
                    boolean updateSuccessfully = dbManager.updateLocation(locationSelected);
                    if (!updateSuccessfully) {
                        showNotification("Không cập nhật được địa điểm");
                        return;
                    }
                    locationSelected = null;
                    showNotification("Cập nhật địa điểm thành công");
                } else {
                    Location location = new Location(locationName);
                    dbManager.addNewLocation(location);
                    showNotification("Đã thêm mới địa điểm "+locationName);

                }
                getListLocations();
                clearInput();
            }
        });
        btnCancel = findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInput();
            }
        });


    }

    private void checkInput(String text) {
        if (TextUtils.isEmpty(text)) {
            btnSave.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
        }
    }

    private void deleteLocation(Location location) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Xóa")
                .setMessage("Xác nhận xóa " + location.getName())
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!dbManager.deleteLocation(location)) {
                            showNotification("Không xóa được " + location.getName());
                            return;
                        }
                        getListLocations();
                        showNotification("Xóa " + location.getName() + " thành công");
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    private void clearInput() {
        edtLocation.setText("");
    }

    private void getListLocations() {
        locations = dbManager.getLocations();
        if (adapter != null) {
            adapter.setLocations(locations);
        }
    }

    private void showNotification(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}