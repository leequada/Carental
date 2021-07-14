package com.example.parking;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.Control.AdapterAllCar;
import com.example.parking.Control.DatabaseDAO;
import com.example.parking.Model.Car;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class home_fragment extends Fragment {
    View root;
    TextView admin;
    EditText name,totalamount;
    Button add,search,viewall;
    RecyclerView recyclerView;
    DatabaseDAO database;
    AdapterAllCar adapterAllCar;
    ArrayList<Car> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home,container,false);
        admin = root.findViewById(R.id.admin);
        name = root.findViewById(R.id.name);
        search = root.findViewById(R.id.searchbtn);
        viewall= root.findViewById(R.id.viewallbtn);
        totalamount = root.findViewById(R.id.totalamount);
        add = root.findViewById(R.id.addbtn);
        recyclerView = root.findViewById(R.id.recyclerviewHome);
        database = new DatabaseDAO(getActivity());
        admin.setText("Admin: "+ MainActivity.u.getName());
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.getData("SELECT * FROM Car");
                list.clear();
                while (cursor.moveToNext()){
                    String id = cursor.getString(0);
                    String namebx = cursor.getString(1);
                    String times = cursor.getString(2);
                    String datein = cursor.getString(3);
                    Car c = new Car(id,namebx,times,datein);
                    list.add(c);

                }
                totalamount.setText(cursor.getCount()+"");
                adapterAllCar.notifyDataSetChanged();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bx = name.getText().toString();
                list.clear();
                List<Car> l  = database.SearchCarbyName(bx);
                for(Car c: l){
                    list.add(c);
                }
                adapterAllCar.notifyDataSetChanged();
            }
        });
        adapterAllCar = new AdapterAllCar(getActivity(),list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterAllCar);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namebx = name.getText().toString();
                namebx = namebx.toUpperCase();
                Calendar calendar2 = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String curentDate = simpleDateFormat.format(calendar2.getTime());
                String timeCurrent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    timeCurrent = java.time.LocalTime.now()+ "";
                }
                timeCurrent = timeCurrent.substring(0,5);
                if(database.checkCar(namebx) == null) {
                    Long result = database.AddCar(new Car("", namebx, timeCurrent, curentDate));
                    if (result > 0) {
                        list.add(new Car("", namebx, timeCurrent, curentDate));
                        adapterAllCar.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    new AlertDialog.Builder(getActivity()).setTitle("Cảnh báo!!!").setMessage("Xe này đã tồn tại trong bãi")
                            .setIcon(android.R.drawable.stat_sys_warning).setNegativeButton("OK",null).show();
                }

            }
        });
        return root;
    }
}
