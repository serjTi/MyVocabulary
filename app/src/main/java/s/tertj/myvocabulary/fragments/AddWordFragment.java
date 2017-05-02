package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import s.tertj.myvocabulary.R;

public class AddWordFragment extends Fragment implements View.OnClickListener {

    EditText etEnglishWord, etRussionWord;
    Button btnAddWord, btnGetTranslate;
    private IOnMyAddWordClickListener addWordClickListener;
    private Gson gson = new GsonBuilder().create();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addWordClickListener = (IOnMyAddWordClickListener) activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_word, container, false);
        etEnglishWord = (EditText) view.findViewById(R.id.etEnglishWord);
        etRussionWord = (EditText) view.findViewById(R.id.etRussionWord);
        btnAddWord = (Button) view.findViewById(R.id.btnAddWord);
        btnAddWord.setOnClickListener(this);
        btnGetTranslate = (Button) view.findViewById(R.id.btnGetTranslate);
        btnGetTranslate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        String englishWord = etEnglishWord.getText().toString();
        String russianWord = etRussionWord.getText().toString();

        switch (v.getId()) {
            case R.id.btnAddWord:
                addWordClickListener.onAddWordClick(englishWord, russianWord);
                break;
            case R.id.btnGetTranslate:
                break;
            default:
                break;
        }
        etEnglishWord.setText("");
        etRussionWord.setText("");
    }

    public interface IOnMyAddWordClickListener {
        void onAddWordClick(String englishWord, String russianWord);
    }
}
