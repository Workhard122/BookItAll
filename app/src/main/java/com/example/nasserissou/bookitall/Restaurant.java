package com.example.nasserissou.bookitall;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Restaurant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Restaurant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    View rootView;

    public Restaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Restaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static Restaurant newInstance(String param1, String param2) {
        Restaurant fragment = new Restaurant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);
       // initImageBitmaps(rootView);
      StringBuilder urlString = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Plattsburgh&key=AIzaSyA7dGiFuWEqIou04m5IxOzs_62IxzKo83I");
        new DownloadFilesTask().execute(urlString.toString());


        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //my data container
    private static final String TAG = "RestaurantActivity";


    private void initRecyclerView(List<RestaurantModel> restaurants) {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        if (restaurants != null) {
            RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), restaurants);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }

    private class DownloadFilesTask extends AsyncTask<String, Void, List<RestaurantModel>> {

        @Override
        protected List<RestaurantModel> doInBackground(String... urls) {

            try {
                List<RestaurantModel> restaurantList = new ArrayList<RestaurantModel>();
                URL myUrl = new URL(urls[0]);
                String jsonString;
                HttpHandler myhandler = new HttpHandler();
                jsonString = myhandler.makeServiceCall(myUrl.toString());

                JSONObject parentObject = new JSONObject(jsonString);
                JSONArray parentArray = parentObject.getJSONArray("results");

                for (int i=0; i < parentArray.length(); i++){
                    RestaurantModel restaurantModel = new RestaurantModel();
                    JSONObject currentObject = parentArray.getJSONObject(i);

                    //String name = currentObject.getString("name");
                    restaurantModel.name = currentObject.getString("name");
                    restaurantModel.imageUrl = currentObject.getString("icon");

                    //restaurantModel.type = currentObject.getString("type");
                    JSONArray typeArray = currentObject.getJSONArray("types");
                    restaurantModel.type = new String[typeArray.length()];

                    for (int k = 0; k < typeArray.length(); k++){
                        restaurantModel.type[k] = typeArray.getString(k);
                    }



                    //adding each restaurant to my list
                    restaurantList.add(restaurantModel);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(List<RestaurantModel> result) {
            initRecyclerView(result);
        }

    }//end of asynctask




}//end of fragement

//https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Plattsburgh&key=AIzaSyA7dGiFuWEqIou04m5IxOzs_62IxzKo83I