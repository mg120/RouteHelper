package com.routehelperr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.routehelperr.R;
import com.routehelperr.activities.RouteServiceDetails;
import com.routehelperr.adapter.OurServicesAdapter;
import com.routehelperr.interfaces.RecyclerOnItemClickListner;
import com.routehelperr.model.ServicesModel;

import java.util.ArrayList;


public class HomeServicesFragment extends Fragment {

    RecyclerView itemServciesRecyclerV;
    OurServicesAdapter adapter;

    ArrayList<ServicesModel.ServiceDataModel> servicesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            servicesList = getArguments().getParcelableArrayList("our_services");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_item_services, container, false);

//        getServicesData();

        itemServciesRecyclerV = view.findViewById(R.id.item_contentServices_recyclerV_id);
        itemServciesRecyclerV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        itemServciesRecyclerV.setHasFixedSize(true);
        // Set Adapter to RecyclerView
        adapter = new OurServicesAdapter(getActivity(), servicesList);
        itemServciesRecyclerV.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerOnItemClickListner() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(getActivity(), RouteServiceDetails.class);
                intent.putExtra("service_data", servicesList.get(position));
                Log.i("image", servicesList.get(position).getImage());
                startActivity(intent);
            }
        });
        return view;
    }
}
