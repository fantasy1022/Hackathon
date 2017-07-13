package com.fantasy1022.hackathon.presentation.type;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fantasy1022.hackathon.R;
import com.fantasy1022.hackathon.presentation.report.ReportActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int[] colors;
    @BindView(R.id.type_road_btn)
    Button typeRoadBtn;
    @BindView(R.id.type_environment_btn)
    Button typeEnvironmentBtn;
    @BindView(R.id.type_tree_btn)
    Button typeTreeBtn;
    @BindView(R.id.type_park_btn)
    Button typeParkBtn;
    @BindView(R.id.type_other_btn)
    Button typeOtherBtn;
    @BindView(R.id.type_question_btn)
    Button typeQuestionBtn;


    public TypeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static TypeFragment newInstance(String param1, String param2) {
//        TypeFragment fragment = new TypeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colors = getActivity().getResources().getIntArray(R.array.colorTypeMaps);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.type_road_btn, R.id.type_environment_btn, R.id.type_tree_btn, R.id.type_park_btn, R.id.type_other_btn, R.id.type_question_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_road_btn:
                startActivity(new Intent(getActivity(), ReportActivity.class));//TODO:bring intent
                break;
            case R.id.type_environment_btn:
                startActivity(new Intent(getActivity(), ReportActivity.class));//TODO:bring intent
                break;
            case R.id.type_tree_btn:
                startActivity(new Intent(getActivity(), ReportActivity.class));//TODO:bring intent
                break;
            case R.id.type_park_btn:
                startActivity(new Intent(getActivity(), ReportActivity.class));//TODO:bring intent
                break;
            case R.id.type_other_btn:
                break;
            case R.id.type_question_btn:
                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
