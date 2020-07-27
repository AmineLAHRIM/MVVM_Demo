package com.am1ne.mvvm.viewmodels;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.am1ne.mvvm.models.NicePlace;
import com.am1ne.mvvm.repositories.NicePlaceRepository;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<NicePlace>> nicePlaces;
    private MutableLiveData<Boolean> isUpdating= new MutableLiveData<>();
    private NicePlaceRepository repo;

    public void init() {
        if (nicePlaces != null) {
            return;
        }
        this.repo = NicePlaceRepository.getInstance();
        this.nicePlaces = this.repo.getDataSet();

    }

    public LiveData<ArrayList<NicePlace>> getNicePlaces() {
        return nicePlaces;
    }

    public void addNewNicePlace(NicePlace nicePlace) {
        this.isUpdating.setValue(true);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ArrayList<NicePlace> currentPlaces = nicePlaces.getValue();
                currentPlaces.add(nicePlace);
                nicePlaces.postValue(currentPlaces);
                isUpdating.postValue(false);
                super.onPostExecute(aVoid);
            }
        }.execute();

    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }
}
