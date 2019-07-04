package com.example.anuj.appointmentrequest.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anuj.appointmentrequest.DashboardActivity;
import com.example.anuj.appointmentrequest.DetailActivity;
import com.example.anuj.appointmentrequest.R;

import java.io.File;
import java.util.List;

/**
 * Created by Anuj on 20-07-2018.
 */

public class doctorsAdapter extends RecyclerView.Adapter<doctorsAdapter.doctorsViewHolder> {



    private Context mCtx;
    private List<doctors> doctors_List;

    public doctorsAdapter(Context mCtx, List<doctors> doctors_List) {
        this.mCtx = mCtx;
        this.doctors_List = doctors_List;

    }

    @Override
    public doctorsAdapter.doctorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.doctors_list, null);
        return new doctorsAdapter.doctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final doctorsAdapter.doctorsViewHolder holder, int position) {
       final doctors doctors = doctors_List.get(position);

        doctors c = doctors_List.get(position);


        Glide.with(mCtx)
                .load(doctors.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(doctors.getTitle());
        holder.textViewShortDesc.setText(doctors.getShortdesc());

        holder.textViewRating.setText(String.valueOf(doctors.getRating()));
        holder.textViewPrice.setText(String.valueOf("INR "+doctors.getPrice()));
        holder.longdesc.setText(String.valueOf(doctors.getLongdesc()));
        holder.textViewdetails.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(view.getContext(), DetailActivity.class);
        i.putExtra("title",doctors.getTitle());
        i.putExtra("longdesc",doctors.getLongdesc());
        i.putExtra("shortdesc",doctors.getShortdesc());
        i.putExtra("rating",doctors.getRating());


        view.getContext().startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return doctors_List.size();
    }

    class doctorsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice,textViewdetails,longdesc,imageText;
        ImageView imageView;

        public doctorsViewHolder(final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);

            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);

            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            textViewdetails=(TextView)itemView.findViewById(R.id.textViewdetails);
           longdesc=(TextView)itemView.findViewById(R.id.longdesc);



        }
    }


}