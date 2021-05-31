package com.masterimran.covidmeter.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.masterimran.covidmeter.Adapter.CovidStatAdapter;
import com.masterimran.covidmeter.Model.StatModel;
import com.masterimran.covidmeter.NetworkCall.ApiClient;
import com.masterimran.covidmeter.R;
import com.masterimran.covidmeter.Utils.LoaderDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LoaderDialog loaderDialog;
    private ArrayList<StatModel> statModelArrayList = new ArrayList<>();
    private RecyclerView statRV;
    private CovidStatAdapter covidStatAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark));
        }
        initViews();
        isOnline(this);
        getStat();

    }

    private void initViews() {
        loaderDialog = new LoaderDialog(this);
        statRV = findViewById(R.id.statRV);
        searchView = findViewById(R.id.searchView);
        statRV.setHasFixedSize(true);
        initRVAdapter();
        searchView.onActionViewExpanded(); //new Added line
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Countries Here");


        if (!searchView.isFocused()) {
            searchView.clearFocus();
        }
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                covidStatAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    private void initRVAdapter() {
        statRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        covidStatAdapter = new CovidStatAdapter(MainActivity.this, statModelArrayList);
        statRV.setAdapter(covidStatAdapter);
    }

    private void getStat() {
        loaderDialog.show();
        Call<ArrayList<StatModel>> arrayListCall = ApiClient.getInstance().getApi().getStat();
        arrayListCall.enqueue(new Callback<ArrayList<StatModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StatModel>> call, Response<ArrayList<StatModel>> response) {
                Log.e(TAG, "onResponse: " + response.raw());
                ArrayList<StatModel> statModels = response.body();
                statModelArrayList.clear();
                statModelArrayList.addAll(statModels);
                covidStatAdapter.notifyDataSetChanged();
                loaderDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<StatModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                loaderDialog.dismiss();
            }
        });
    }

    public boolean isOnline(final Context ctx) {
        if (ctx == null)
            return false;

        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        Toast.makeText(ctx, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        return false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnline(this);
        Log.e(TAG, "onResume: ");
        //  searchview focus remove
        if (searchView != null) {
            searchView.setQuery("Search Countries Here", false);
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
    }
}