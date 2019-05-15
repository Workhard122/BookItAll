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
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Events.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Events extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    View rootView;

    public Events() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Events.
     */
    // TODO: Rename and change types and number of parameters
    public static Events newInstance(String param1, String param2) {
        Events fragment = new Events();
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
        rootView = inflater.inflate(R.layout.fragment_events, container, false);

        StringBuilder urlString = new StringBuilder("https://www.eventbriteapi.com/v3/events/search/?token=GG73O6KNCGQA3HKUPXAA&location.address=plattsburgh,NY&expand=venue");

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

    private static final String TAG = "EnventActivity";

    private void initRecyclerView(List<EventModel> events) {
        Log.d(TAG, "EVENT, initRecyclerView: init recyclerview");
        if (events != null) {
            RecyclerView recyclerView = rootView.findViewById(R.id.event_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            EventRecyclerViewAdapter adapter = new EventRecyclerViewAdapter(getContext(), events);
            recyclerView.setAdapter(adapter);

        }

    }


    private class DownloadFilesTask extends AsyncTask<String, Void, List<EventModel>> {

        @Override
        protected List<EventModel> doInBackground(String... urls) {
            try {
                List<EventModel> eventList = new ArrayList<EventModel>();
                URL myUrl = new URL(urls[0]);
                String jsonString;
                HttpHandler myhandler = new HttpHandler();
                jsonString = myhandler.makeServiceCall(myUrl.toString());

                JSONObject parentObject = new JSONObject(jsonString);
                JSONArray parentArray = parentObject.getJSONArray("events");

                Log.d("parrent object", Integer.toString(parentArray.length()) );

                for (int i=0; i < parentArray.length(); i++){
                    EventModel eventModel = new EventModel();
                    JSONObject currentObject = parentArray.getJSONObject(i);


                    JSONObject nameObj = currentObject.getJSONObject("name");
                    eventModel.name = nameObj.getString("text");

                    if(currentObject.has("logo") && !currentObject.isNull("logo")) {
                        JSONObject imageObj = currentObject.getJSONObject("logo");
                        eventModel.ImageUrl = imageObj.getString("url");
                    }

                    JSONObject descriptionObj = currentObject.getJSONObject("description");
                    eventModel.Description = descriptionObj.getString("text");

                    JSONObject venueObj = currentObject.getJSONObject("venue");
                    JSONObject addrObj = venueObj.getJSONObject("address");
                    eventModel.Location = addrObj.getString("localized_address_display");

                    JSONObject dateObj = currentObject.getJSONObject("start");
                    String full_local =  dateObj.getString("local");

                    StringTokenizer tokens = new StringTokenizer(full_local, ":");
                    eventModel.Date = tokens.nextToken() ;


                    //adding each restaurant to my list
                    eventList.add(eventModel);

                }

                //return list of events
                return eventList;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(List<EventModel> result) {
            initRecyclerView(result);
        }

    }

}//event class ends.
//https://www.eventbriteapi.com/v3/events/search/?token=GG73O6KNCGQA3HKUPXAA&location.address=plattsburgh,NY&expand=venue
