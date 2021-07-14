package com.example.parking;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parking.Control.AdapterListView;
import com.example.parking.Control.DatabaseDAO;
import com.example.parking.Model.Car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class notification_fragment extends Fragment {
    View view;
    ListView listView;
    DatabaseDAO databaseDAO;
    AdapterListView adapterListView;
    ArrayList<String> list= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications,container,false);
        listView = view.findViewById(R.id.listviews);
        databaseDAO = new DatabaseDAO(getActivity());

        Cursor cursor = databaseDAO.getData("SELECT * FROM Car");
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String namebx = cursor.getString(1);
            String times = cursor.getString(2);
            String datein = cursor.getString(3);
            Long result= 0L;
            try {
                result = CaculatorFee(times,datein);
            } catch (ParseException e) {
                Log.d("ERROR",e.getMessage());
            }
            if(result > 24){
                list.add(namebx);
            }

        }
        adapterListView = new AdapterListView(list);
        listView.setAdapter(adapterListView);


        return view;
    }
    public Long CaculatorFee(String timeIn, String dateIn) throws ParseException {
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String curentDate = simpleDateFormat.format(calendar2.getTime());
        Date date1= simpleDateFormat.parse(curentDate);
        Date date2 =simpleDateFormat.parse(dateIn);
        String timeCurrent = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timeCurrent = java.time.LocalTime.now()+ "";
        }
        timeCurrent = timeCurrent.substring(0,5);
        String[] arr1 = timeCurrent.split(":");
        String[] arr2 = timeIn.split(":");
        float t1 = Long.parseLong(arr2[0]) + Long.parseLong(arr2[1])*(1/60);
        float t2 = Long.parseLong(arr1[0]) + Long.parseLong(arr1[1])*(1/60);
        float total = t2-t1;
        Long diff = (date1.getTime()-date2.getTime())/3600000;
        Long result = diff - (long)total;
        return result;
    }
}
