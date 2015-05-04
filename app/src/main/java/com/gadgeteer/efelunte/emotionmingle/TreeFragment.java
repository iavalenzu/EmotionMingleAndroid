package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree, container, false);

        ImageView imageViewBackground = (ImageView) view.findViewById(R.id.imageViewBackground);


        ImageView imageViewLeaf1 = (ImageView) view.findViewById(R.id.imageViewLeaf1);
        ImageView imageViewLeaf2 = (ImageView) view.findViewById(R.id.imageViewLeaf2);
        ImageView imageViewLeaf3 = (ImageView) view.findViewById(R.id.imageViewLeaf3);
        ImageView imageViewLeaf4 = (ImageView) view.findViewById(R.id.imageViewLeaf4);
        ImageView imageViewLeaf5 = (ImageView) view.findViewById(R.id.imageViewLeaf5);
        ImageView imageViewLeaf6 = (ImageView) view.findViewById(R.id.imageViewLeaf6);
        ImageView imageViewLeaf7 = (ImageView) view.findViewById(R.id.imageViewLeaf7);
        ImageView imageViewLeaf8 = (ImageView) view.findViewById(R.id.imageViewLeaf8);

        imageViewLeaf1.setImageResource(getLeaf(0));
        imageViewLeaf2.setImageResource(getLeaf(1));
        imageViewLeaf3.setImageResource(getLeaf(2));
        imageViewLeaf4.setImageResource(getLeaf(3));
        imageViewLeaf5.setImageResource(getLeaf(4));
        imageViewLeaf6.setImageResource(getLeaf(5));
        imageViewLeaf7.setImageResource(getLeaf(6));
        imageViewLeaf8.setImageResource(getLeaf(7));

        return view;
    }

    public void onButtonPressed(Uri uri) {
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
