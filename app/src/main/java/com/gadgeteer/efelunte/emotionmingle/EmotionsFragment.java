package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.io.IOException;


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

    private User loggedUser;
    private TextView labelEstadoActual;
    private ImageView imageviewCurrentFace;

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

        loggedUser = session.getUser();

        labelEstadoActual = (TextView) root.findViewById(R.id.label_estado_actual);
        labelEstadoActual.setText(loggedUser.getFirstname() + " se sentía");

        imageviewCurrentFace =  (ImageView) root.findViewById(R.id.imageview_current_face);

        if(loggedUser != null)
        {
            Emotion lastEmotion = loggedUser.getLastEmotion();

            if(lastEmotion != null)
            {
                setCurrentFace(lastEmotion);
            }

        }

        final ImageView buttons = (ImageView) root.findViewById(R.id.buttons);

        buttons.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int touchX = (int) event.getX();
                int touchY = (int) event.getY();

                int buttonsWidth = buttons.getWidth();
                int buttonsHeight = buttons.getHeight();

                int column = -1;
                int row = -1;

                if(touchX < buttonsWidth/4)
                {
                    column = 0;
                }
                else if(touchX < buttonsWidth/2)
                {
                    column = 1;
                }
                else if(touchX < 3*buttonsWidth/4)
                {
                    column = 2;
                }
                else if(touchX < buttonsWidth)
                {
                    column = 3;
                }

                if(touchY < buttonsHeight/2)
                {
                    row = 0;
                }
                else if(touchY < buttonsHeight)
                {
                    row = 1;
                }

                int buttonPos = 4 * row + column;

                switch (buttonPos)
                {
                    case 0:
                        botonEstresado();
                        break;
                    case 1:
                        botonIrritado();
                        break;
                    case 2:
                        botonFeliz();
                        break;
                    case 3:
                        botonEnergico();
                        break;
                    case 4:
                        botonAgotado();
                        break;
                    case 5:
                        botonTriste();
                        break;
                    case 6:
                        botonTranquilo();
                        break;
                    case 7:
                        botonRelajado();
                        break;
                }

                try {
                    MainApp.updateBar();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        return root;
    }


    public void setCurrentFace(Emotion emotion)
    {

        if(emotion == null)
        {
            imageviewCurrentFace.setImageResource(0);
        }
        else if(emotion.isType(Emotion.STRESSED))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_estresado);
        }
        else if(emotion.isType(Emotion.ANGRY))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_irritado);
        }
        else if(emotion.isType(Emotion.HAPPY))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_feliz);
        }
        else if(emotion.isType(Emotion.ENERGETIC))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_energico);
        }
        else if(emotion.isType(Emotion.TIRED))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_agotado);
        }
        else if(emotion.isType(Emotion.SAD))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_triste);
        }
        else if(emotion.isType(Emotion.CALMED))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_tranquilo);
        }
        else if(emotion.isType(Emotion.RELAXED))
        {
            imageviewCurrentFace.setImageResource(R.drawable.cara_relajado);
        }
        else
        {
            imageviewCurrentFace.setImageResource(0);
        }


    }

    private void botonRelajado()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.RELAXED, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "RELAXED: " + loggedUser.getEmotionCount(Emotion.RELAXED));

            setCurrentFace(emotion);
        }

    }

    private void botonTranquilo()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.CALMED, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "CALMED: " + loggedUser.getEmotionCount(Emotion.CALMED));

            setCurrentFace(emotion);
        }

    }

    private void botonTriste()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.SAD, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "SAD: " + loggedUser.getEmotionCount(Emotion.SAD));

            setCurrentFace(emotion);
        }

    }

    private void botonAgotado()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.TIRED, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "TIRED: " + loggedUser.getEmotionCount(Emotion.TIRED));

            setCurrentFace(emotion);
        }
    }

    private void botonEnergico()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.ENERGETIC, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "ENERGETIC: " + loggedUser.getEmotionCount(Emotion.ENERGETIC));

            setCurrentFace(emotion);
        }
    }

    private void botonFeliz()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.HAPPY, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "HAPPY: " + loggedUser.getEmotionCount(Emotion.HAPPY));

            setCurrentFace(emotion);
        }
    }

    private void botonIrritado()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.ANGRY, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "ANGRY: " + loggedUser.getEmotionCount(Emotion.ANGRY));

            setCurrentFace(emotion);
        }
    }


    public void botonEstresado()
    {
        if(loggedUser != null)
        {
            Emotion emotion = new Emotion(Emotion.STRESSED, loggedUser);
            emotion.save();

            Log.v(EmotionMingle.TAG, "STRESSED: " + loggedUser.getEmotionCount(Emotion.STRESSED));

            setCurrentFace(emotion);
        }
    }


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
