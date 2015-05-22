package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Location;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Location>> {

    private static final int ARG_SECTION_NUMBER = 4;

    static private final int LOADER_ID = 123456;

    LocationsAdapter locationsAdapter;

    private User loggedUser;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocationsFragment.
     */
    public static LocationsFragment newInstance() {
        LocationsFragment fragment = new LocationsFragment();
        return fragment;
    }

    public LocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Session session = Util.getSession();

        if(session != null)
        {
            loggedUser = session.getUser();

            if(loggedUser != null)
            {
                List<Location> locations = loggedUser.getLocations();

                locationsAdapter = new LocationsAdapter(getActivity(), locations);

                setListAdapter(locationsAdapter);

                // Prepare the loader.  Either re-connectToWebSocket with an existing one,
                // or start a new one.
                getLoaderManager().initLoader(LOADER_ID, null, this);


            }


        }



        setHasOptionsMenu(true);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_locations, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add_location:

                addNewLocation();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void addNewLocation()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Agregar Ubicacion");
        alert.setMessage("Ingresa la descripcion y el tipo de la nueva ubicacion");


        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.alertdialog_new_location, null);
        alert.setView(view);


        final EditText locationDescription = (EditText) view.findViewById(R.id.edittext_location_description);

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_location_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                String description = locationDescription.getText().toString();
                String type = (String) spinner.getSelectedItem();

                if(description.isEmpty() || type.isEmpty())
                {
                    return;
                }

                Location location = new Location(loggedUser, description, type, 0, 0);
                location.save();

                getLoaderManager().getLoader(LOADER_ID).forceLoad();

            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.i(EmotionMingle.TAG, "onListItemClick");


        Location location = locationsAdapter.getItem(position);

        if(!location.isSelected())
        {
            if(loggedUser != null)
            {
                loggedUser.deselectAllLocations();
            }

            location.setSelected(true);
        }

        location.save();

        getLoaderManager().getLoader(LOADER_ID).forceLoad();

    }



    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        Button buttonAddLocation = (Button) view.findViewById(R.id.button_add_location);

        buttonAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Agregar Ubicacion");
                alert.setMessage("Ingresa la descripcion y el tipo de la nueva ubicacion");


                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = inflater.inflate(R.layout.alertdialog_new_location, null);
                alert.setView(view);


                final EditText locationDescription = (EditText) view.findViewById(R.id.edittext_location_description);

                final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_location_type);
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations_type_array, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);


                alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        String description = locationDescription.getText().toString();
                        String type = (String) spinner.getSelectedItem();

                        if(description.isEmpty() || type.isEmpty())
                        {
                            return;
                        }

                        Location location = new Location(description, type, 0, 0);
                        location.save();

                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();


            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    */

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((EmotionMingle) activity).onSectionAttached(ARG_SECTION_NUMBER);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<List<Location>> onCreateLoader(int id, Bundle args) {
        return new LocationLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Location>> loader, List<Location> data) {

        locationsAdapter.clear();
        locationsAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Location>> loader) {

        locationsAdapter.clear();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
