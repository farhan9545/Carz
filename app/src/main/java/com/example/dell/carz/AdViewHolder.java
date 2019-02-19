package com.example.dell.carz;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name;
    public TextView city;
    public ImageView Image;
    public TextView mileage;
    public TextView price;
    public TextView date;
    public AdViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.textView);
        city = (TextView) view.findViewById(R.id.textView2);
        Image = (ImageView) view.findViewById(R.id.imageView);
        mileage = (TextView) view.findViewById(R.id.textView5);
        date = (TextView) view.findViewById(R.id.textView7);
        price = (TextView) view.findViewById(R.id.textView6);
        view.setOnClickListener(this);

    }

    public void setValues(AdView ad)
    {
        name.setText(ad.getVehicleName());
        Image.setImageBitmap(ad.getPic());
        price.setText(ad.getPrice());
        date.setText(ad.getDatee());
        city.setText(ad.getCity());
        mileage.setText(ad.getMileage());

    }

    @Override
    public void onClick(View view) {

    }
}
