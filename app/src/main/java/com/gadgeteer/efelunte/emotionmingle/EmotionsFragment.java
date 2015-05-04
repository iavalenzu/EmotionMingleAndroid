package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gadgeteer.efelunte.emotionmingle.model.Emotions;
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
public class EmotionsFragment extends Fragment {

    private static final int ARG_SECTION_NUMBER = 2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmotionsFragment newInstance() {
        EmotionsFragment fragment = new EmotionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EmotionsFragment() {
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
        View root =  inflater.inflate(R.layout.fragment_emotions, container, false);

        Session session = Util.getSession();

        final User loggedUser = session.getUser();

        final TextView textViewEstadoActual = (TextView) root.findViewById(R.id.texto_estado_actual);

        if(loggedUser != null)
        {
            Emotions emotions = loggedUser.getEmotions();

            if(emotions != null)
            {
                textViewEstadoActual.setText(emotions.getLastEmotion());
            }
            else
            {
                Log.i(EmotionMingle.TAG, "Emotions is NULL!!");
            }
        }


        Button buttonAgotado = (Button)root.findViewById(R.id.button_agotado);

        buttonAgotado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loggedUser != null)
                {
                    Emotions emotions = loggedUser.getEmotions();

                    if(emotions != null){
                        emotions.setTired(emotions.getTired() + 1);
                        emotions.setLastEmotion("Agotado");
                        emotions.save();
                        textViewEstadoActual.setText("Agotado");
                        Log.i(EmotionMingle.TAG, "Guardaste Agotado");

                    } else
                    {
                        Log.i(EmotionMingle.TAG, "Emotions is NULL!!");
                    }

                    loggedUser.save();
                }

            }
        });

        Button buttonEnergico = (Button)root.findViewById(R.id.button_energico);

        buttonEnergico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loggedUser != null)
                {
                    Emotions emotions = loggedUser.getEmotions();

                    if(emotions != null){
                        emotions.setEnergetic(emotions.getEnergetic() + 1);
                        emotions.setLastEmotion("Energico");
                        emotions.save();
                        textViewEstadoActual.setText("Energico");
                        Log.i(EmotionMingle.TAG, "Guardaste Energico");
                    } else
                    {
                        Log.i(EmotionMingle.TAG, "Emotions is NULL!!");
                    }

                    loggedUser.save();
                }

            }
        });

        Button buttonEnojado = (Button)root.findViewById(R.id.button_enojado);

        buttonEnojado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loggedUser != null)
                {
                    Emotions emotions = loggedUser.getEmotions();

                    if(emotions != null){
                        emotions.setAngry(emotions.getAngry() + 1);
                        emotions.setLastEmotion("Enojado");
                        emotions.save();
                        textViewEstadoActual.setText("Enojado");
                        Log.i(EmotionMingle.TAG, "Guardaste Enojado");
                    } else
                    {
                        Log.i(EmotionMingle.TAG, "Emotions is NULL!!");
                    }

                    loggedUser.save();
                }

            }
        });



        return root;
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
