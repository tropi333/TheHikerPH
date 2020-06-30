package com.pccw.hikerph;


import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Helper.Properties;
import com.pccw.hikerph.RoomDatabase.MyDatabase;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;
import com.pccw.hikerph.adapter.MyHikeAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyHikeFragment extends Fragment implements MyHikeAdapter.OnMyHikeEventClickListener {

    private static final String TAG = "MyHikeFragment";

    TextView tvEmptyData;

    RecyclerView recyclerView;

    MyHikeAdapter myHikeAdapter;

    List<HikeDto> hikeList;

    HikeDto deletedHike = null;



    private MyHikeViewModel myHikeViewModel;

    public MyHikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_hike, container, false);

        initFields(view);
        initViewModel();


        return  view;
    }

    private void initFields(View view){

        tvEmptyData = view.findViewById(R.id.tvEmptyData);

        recyclerView = view.findViewById(R.id.recycler_view_my_hikes);
        recyclerView.setAdapter(myHikeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void initViewModel(){

        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);
        myHikeViewModel.getAllHikes().observe(getViewLifecycleOwner(), new Observer<List<HikeDto>>() {
            @Override
            public void onChanged(List<HikeDto> hikeDtos) {

                hikeList = hikeDtos;

                refreshAdapter(hikeList);

                for (HikeDto d:hikeDtos) {
                    System.out.println("JOMAS"+d.getDtStartDate());

                }
                if(hikeList.size() == 0){

                    tvEmptyData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else{

                    tvEmptyData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void refreshAdapter(List<HikeDto> hikeDtos){

        myHikeAdapter = new MyHikeAdapter(hikeDtos,this);
        recyclerView.setAdapter(myHikeAdapter);
    }

    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            deletedHike = hikeList.get(position);
            myHikeViewModel.delete(deletedHike);

            String message = getString(R.string.snack_message_hike_deleted);
            String action_title = getString(R.string.snack_message_undo );

            Snackbar.make(recyclerView,message,Snackbar.LENGTH_SHORT)
                    .setAction(action_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myHikeViewModel.insert(deletedHike);
                        }
                    }).show();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.swipeBackground))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeLeftLabel("Delete")
                    .setSwipeLeftLabelTextSize(1,18)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public void onMyHikeClickListener(int index_hike) {

        showHikeEditActivity(index_hike);

    }

    private  void showHikeEditActivity(int index_hike){

        Intent intent = new Intent(getContext(), EditHikeActivity.class);
        HikeDto hikeDto = hikeList.get(index_hike);
        intent.putExtra("hikeDto", hikeDto);
        intent.putExtra("index_hike",index_hike);
        startActivityForResult(intent, Properties.REQUEST_CODE_EDIT_HIKE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        myHikeAdapter.notifyDataSetChanged();

    }




}
