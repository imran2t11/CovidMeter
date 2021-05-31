package com.masterimran.covidmeter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterimran.covidmeter.Model.StatModel;
import com.masterimran.covidmeter.R;

import java.util.ArrayList;

public class CovidStatAdapter extends RecyclerView.Adapter<CovidStatAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<StatModel> statModelArrayList, filteredList;

    public CovidStatAdapter(Context context, ArrayList<StatModel> statModelArrayList) {
        this.context = context;
        this.statModelArrayList = statModelArrayList;
        this.filteredList = statModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.covid_stat_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CovidStatAdapter.MyViewHolder holder, int position) {
        StatModel statModel = filteredList.get(position);
        holder.country_TV.setText(statModel.getCountry().toString());
        holder.today_val_TV.setText(statModel.getTodayCases().toString());
        holder.cases_value_TV.setText(statModel.getCases().toString());
        holder.todayDeaths_value_TV.setText(statModel.getTodayDeaths().toString());
        holder.totalDeaths_value_TV.setText(statModel.getDeaths().toString());
        holder.recovered_value_TV.setText(statModel.getRecovered().toString());
        holder.active_value_TV.setText(statModel.getActive().toString());
        holder.critical_value_TV.setText(statModel.getCritical().toString());
        holder.totalTests_value_TV.setText(statModel.getTotalTests().toString());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = statModelArrayList;
                } else {
                    ArrayList<StatModel> filtered_List = new ArrayList<>();
                    for (StatModel row : statModelArrayList) {


                        if (row.getCountry().toLowerCase().contains(charString.toLowerCase())) {
                            filtered_List.add(row);
                        }
                    }

                    filteredList = filtered_List;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<StatModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView country_TV, today_val_TV, todayDeaths_value_TV, recovered_value_TV, critical_value_TV, cases_value_TV, totalDeaths_value_TV, active_value_TV, totalTests_value_TV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country_TV = itemView.findViewById(R.id.country_TV);
            today_val_TV = itemView.findViewById(R.id.today_val_TV);
            todayDeaths_value_TV = itemView.findViewById(R.id.todayDeaths_value_TV);
            recovered_value_TV = itemView.findViewById(R.id.recovered_value_TV);
            critical_value_TV = itemView.findViewById(R.id.critical_value_TV);
            cases_value_TV = itemView.findViewById(R.id.cases_value_TV);
            totalDeaths_value_TV = itemView.findViewById(R.id.totalDeaths_value_TV);
            active_value_TV = itemView.findViewById(R.id.active_value_TV);
            totalTests_value_TV = itemView.findViewById(R.id.totalTests_value_TV);
        }
    }
}
