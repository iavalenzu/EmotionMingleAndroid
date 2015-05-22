package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Leafs;
import com.gadgeteer.efelunte.emotionmingle.model.Location;
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
public class TreeFragment extends Fragment {


    private static final int ARG_SECTION_NUMBER = 0;


    private User loggedUser;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    public static TreeFragment newInstance() {
        TreeFragment fragment = new TreeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(EmotionMingle.TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_tree, container, false);

        ImageView imageViewBackground = (ImageView) view.findViewById(R.id.imageViewScenario);

        ImageView imageViewStar = (ImageView) view.findViewById(R.id.imageViewStar);

        ImageView imageViewLeaf1 = (ImageView) view.findViewById(R.id.imageViewLeaf1);
        ImageView imageViewLeaf2 = (ImageView) view.findViewById(R.id.imageViewLeaf2);
        ImageView imageViewLeaf3 = (ImageView) view.findViewById(R.id.imageViewLeaf3);
        ImageView imageViewLeaf4 = (ImageView) view.findViewById(R.id.imageViewLeaf4);
        ImageView imageViewLeaf5 = (ImageView) view.findViewById(R.id.imageViewLeaf5);
        ImageView imageViewLeaf6 = (ImageView) view.findViewById(R.id.imageViewLeaf6);
        ImageView imageViewLeaf7 = (ImageView) view.findViewById(R.id.imageViewLeaf7);
        ImageView imageViewLeaf8 = (ImageView) view.findViewById(R.id.imageViewLeaf8);


        Session session = Util.getSession();

        if(session != null)
        {
            loggedUser = session.getUser();

            if(loggedUser != null)
            {
                Emotion lastEmotion = loggedUser.getLastEmotion();

                if(lastEmotion != null)
                {

                    Log.v(EmotionMingle.TAG, "LastEmotion: " + lastEmotion.getType());

                    imageViewStar.setImageResource(getStar(lastEmotion));

                }

                Location location = loggedUser.getSelectedLocation();

                if(location != null)
                {
                    Log.v(EmotionMingle.TAG, "SelectedLocation: " + location.getType());

                    imageViewBackground.setImageResource(getBackground(location));


                }


            }

            Leafs leafs = session.getLeafs();

            if(leafs != null)
            {
                imageViewLeaf1.setImageResource(getLeaf(leafs.getLeaf1()));
                imageViewLeaf2.setImageResource(getLeaf(leafs.getLeaf2()));
                imageViewLeaf3.setImageResource(getLeaf(leafs.getLeaf3()));
                imageViewLeaf4.setImageResource(getLeaf(leafs.getLeaf4()));
                imageViewLeaf5.setImageResource(getLeaf(leafs.getLeaf5()));
                imageViewLeaf6.setImageResource(getLeaf(leafs.getLeaf6()));
                imageViewLeaf7.setImageResource(getLeaf(leafs.getLeaf7()));
                imageViewLeaf8.setImageResource(getLeaf(leafs.getLeaf8()));

            }


        }






        return view;
    }

    private int getBackground(Location location) {

        String type = location.getType();

        if(type.equals(Location.TYPE_CUIDADO))
        {
            return R.drawable.hospital_escenario;
        }
        else if(type.equals(Location.TYPE_DEPORTE))
        {
            return R.drawable.deportivo_escenario;
        }
        else if(type.equals(Location.TYPE_PERSONAL))
        {
            return R.drawable.personal_escenario;
        }
        else if(type.equals(Location.TYPE_RECREACIONAL))
        {
            return R.drawable.social_escenario;
        }
        else
        {
            return R.drawable.social_escenario;
        }

    }

    public void onButtonPressed(Uri uri) {
    }


    public int getStar(Emotion emotion)
    {
        if(emotion == null)
        {
            return R.drawable.solyluna2_07;
        }
        else if(emotion.isType(Emotion.SAD))
        {
            return R.drawable.solyluna2_08;
        }
        else if(emotion.isType(Emotion.TIRED))
        {
            return R.drawable.solyluna2_06;
        }
        else if(emotion.isType(Emotion.STRESSED))
        {
            return R.drawable.solyluna2_04;
        }
        else if(emotion.isType(Emotion.ANGRY))
        {
            return R.drawable.solyluna2_02;
        }
        else if(emotion.isType(Emotion.HAPPY))
        {
            return R.drawable.solyluna2_07;
        }
        else if(emotion.isType(Emotion.ENERGETIC))
        {
            return R.drawable.solyluna2_03;
        }
        else if(emotion.isType(Emotion.RELAXED))
        {
            return R.drawable.solyluna2_05;
        }
        else if(emotion.isType(Emotion.CALMED))
        {
            return R.drawable.solyluna2_01;
        }
        else
        {
            return R.drawable.solyluna2_07;
        }
    }

    public int getLeaf(int level)
    {
        switch (level)
        {
            case 0:
                return R.drawable.hojas_08;
            case 1:
                return R.drawable.hojas_05;
            case 2:
                return R.drawable.hojas_04;
            case 3:
                return R.drawable.hojas_06;
            case 4:
                return R.drawable.hojas_07;
            case 5:
                return R.drawable.hojas_02;
            case 6:
                return R.drawable.hojas_01;
            case 7:
                return R.drawable.hojas_03;
            default:
                return R.drawable.hojas_03;
        }

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
        public void onFragmentInteraction(Uri uri);
    }

}
