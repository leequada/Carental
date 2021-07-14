package com.example.parking.Control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.Model.Car;
import com.example.parking.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterAllCar extends RecyclerView.Adapter<AdapterAllCar.ViewHolder>{
    Context context;
    ArrayList<Car> list;

    public AdapterAllCar(Context context, ArrayList<Car> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car c = list.get(position);
        holder.name.setText(c.getBienxe());
        holder.times.setText(c.getTime());
        holder.dates.setText(c.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,times,dates;
        Button delete,update;
        EditText bx;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.namebx);
            times = itemView.findViewById(R.id.times);
            dates = itemView.findViewById(R.id.datein);
            delete = itemView.findViewById(R.id.btnDelete);
            update = itemView.findViewById(R.id.btnUpdate);
            bx = itemView.findViewById(R.id.bx);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long result= 100L;
                    try {
                         result = CaculatorFee(list.get(getPosition()).getTime(),list.get(getPosition()).getDate())*1000;
                    } catch (ParseException e) {
                        Log.d("ERROR",e.getMessage());
                    }
                    String toMoney = String.format("%,.1f", (double) result)+ " vnd";
                    new AlertDialog.Builder(context).setTitle("Thu phí xe")
                           .setMessage("Tổng chi phí gửi xe của bạn hết : "+ toMoney )
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseDAO databaseDAO = new DatabaseDAO(context);
                                    int value = databaseDAO.DeleteCar(list.get(getPosition()).getBienxe());
                                    if(value > 0) {
                                        list.remove(list.get(getPosition()));
                                        notifyDataSetChanged();
                                    }
                                }
                            }).setNegativeButton("No",null).setIcon(android.R.drawable.ic_input_delete).show();
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(name.getVisibility() == View.VISIBLE){
                        name.setVisibility(View.GONE);
                        bx.setVisibility(View.VISIBLE);
                    }else {
                        new AlertDialog.Builder(context).setTitle("Update Car")
                                .setMessage("Bạn có muốn thay đổi tên biển số xe")
                                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                         DatabaseDAO databaseDAO = new DatabaseDAO(context);
                                         String id = list.get(getPosition()).getId();
                                        String date = list.get(getPosition()).getDate();
                                        String time = list.get(getPosition()).getTime();
                                        String names = bx.getText().toString();
                                        Car c = new Car(id,names,time,date);
                                         int result = databaseDAO.UpdateCar(c);
                                         if(result > 0){
                                             name.setVisibility(View.VISIBLE);
                                             bx.setVisibility(View.GONE);
                                             list.set(getPosition(),c);
                                             notifyDataSetChanged();

                                         }
                                    }
                                }).setNegativeButton("No",null).show();
                    }

                }
            });

        }
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
        Long diff = (date1.getTime()-date2.getTime())/86400000;
        Long result = diff - (long)total;
        return result;
    }

}
