package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final int ARG_SECTION_NUMBER = 1;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Session session = Util.getSession();

        final User user = session.getUser();

        final EditText firstNameEditText = (EditText)view.findViewById(R.id.name);

        if(user != null && !user.getFirstname().isEmpty()){
            firstNameEditText.setText(user.getFirstname());
        }

        firstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String value = firstNameEditText.getText().toString();

                if (hasFocus) {
                    if (value.trim().equals("Nombre")) {
                        firstNameEditText.setText("");
                    }
                } else {
                    if (value.trim().equals("")) {
                        firstNameEditText.setText("Nombre");
                    }
                }
            }
        });

        final EditText lastNameEditText = (EditText)view.findViewById(R.id.lastname);

        if(user != null && !user.getLastname().isEmpty()){
            lastNameEditText.setText(user.getLastname());
        }



        lastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String value = lastNameEditText.getText().toString();

                if (hasFocus) {
                    if (value.trim().equals("Apellido")) {
                        lastNameEditText.setText("");
                    }
                } else {
                    if (value.trim().equals("")) {
                        lastNameEditText.setText("Apellido");
                    }
                }
            }
        });


        final EditText birthdayEditText = (EditText)view.findViewById(R.id.birthday);


        birthdayEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String value = birthdayEditText.getText().toString();

                if (hasFocus) {
                    if (value.trim().equals("Fecha nacimiento")) {
                        birthdayEditText.setText("");
                    }
                } else {
                    if (value.trim().equals("")) {
                        birthdayEditText.setText("Fecha nacimiento");
                    }
                }
            }
        });

        if(user != null && !user.getBirthday().isEmpty()){
            birthdayEditText.setText(user.getBirthday());
        }

        Button updateProfileButton = (Button)view.findViewById(R.id.update_profile);

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String birthday = birthdayEditText.getText().toString();

                if(user != null)
                {
                    user.setFirstname(firstName);
                    user.setLastname(lastName);
                    user.setBirthday(birthday);
                    user.save();

                    Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_LONG).show();

                }

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((EmotionMingle) activity).onSectionAttached(ARG_SECTION_NUMBER);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
