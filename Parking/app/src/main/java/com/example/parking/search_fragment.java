package com.example.parking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.Control.DatabaseDAO;
import com.example.parking.Model.Car;

import java.util.ArrayList;
import java.util.List;

public class search_fragment extends Fragment {
    View view;
    EditText content;
    Button btnSearch;
    RecyclerView recyclerView;

    ArrayList<Car> arrayList = new ArrayList<>();
    DatabaseDAO databaseDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        content = view.findViewById(R.id.edSearch);
        btnSearch = view.findViewById(R.id.btnSearchV);
        recyclerView = view.findViewById(R.id.recyclers);

        databaseDAO = new DatabaseDAO(getActivity());





        return view;
    }
}
