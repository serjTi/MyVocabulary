package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import s.tertj.myvocabulary.R;

public class StartFragment extends Fragment implements View.OnClickListener {

    Button btnEditVocabulary, btnMyVocabulary, btnLearnWords;
    private IOnMyStartFragmentClickListener myStartFragmentClickListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myStartFragmentClickListener = (IOnMyStartFragmentClickListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        btnEditVocabulary = (Button) view.findViewById(R.id.btnEditVocabulary);
        btnEditVocabulary.setOnClickListener(this);

        btnMyVocabulary = (Button) view.findViewById(R.id.btnMyVocabulary);
        btnMyVocabulary.setOnClickListener(this);

        btnLearnWords = (Button) view.findViewById(R.id.btnLearnWords);
        btnLearnWords.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditVocabulary:
                myStartFragmentClickListener.onAddFragmentButtonClick();
                break;

            case R.id.btnMyVocabulary:
                myStartFragmentClickListener.onMyVocabularyButtonClick();
                break;

            case R.id.btnLearnWords:
                myStartFragmentClickListener.onLearnWordsButtonClick();
                break;
            default:
                break;
        }

    }
    public interface IOnMyStartFragmentClickListener {
        void onAddFragmentButtonClick();
        void onMyVocabularyButtonClick();
        void onLearnWordsButtonClick();
    }
}
