package com.example.dell.carz;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    static List<Car_name> Car_nameList =  new ArrayList<Car_name>();
    private ArrayList<Car_name> arraylist;

    public ListViewAdapter(Context context, List<Car_name> Car_nameList) {
        mContext = context;
        this.Car_nameList = Car_nameList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Car_name>();
        this.arraylist.addAll(Car_nameList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return Car_nameList.size();
    }

    @Override
    public Car_name getItem(int position) {
        return Car_nameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(Car_nameList.get(position).getCarname());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Car_nameList.clear();
        if (charText.length() == 0) {
            Car_nameList.addAll(arraylist);
        } else {
            for (Car_name wp : arraylist) {
                if (wp.getCarname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    Car_nameList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

