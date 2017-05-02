package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import s.tertj.myvocabulary.AppConstants;
import s.tertj.myvocabulary.R;
import s.tertj.myvocabulary.data.Word;

public class LearnWordsFragment extends Fragment implements View.OnClickListener {

    private LinkedList<Word> myVocabularyList = new LinkedList<Word>();
    private Word word;
    private int size;
    private Button btnTest, btnHelp;
    private TextView tvTask;
    private EditText etAnswer;

    public static LearnWordsFragment newInstance(LinkedList<Word> vocabularyLinkedList) {
        LearnWordsFragment learnWordsFragment = new LearnWordsFragment();
        Bundle args = new Bundle();
        for (int i = 0; i < vocabularyLinkedList.size(); i++) {
            args.putParcelable("Word " + i, vocabularyLinkedList.get(i));
        }
        learnWordsFragment.setArguments(args);
        return learnWordsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            for (int i = 0; i < getArguments().size(); i++) {
                Word word = getArguments().getParcelable("Word " + i);
                if (word != null) {
                    myVocabularyList.add(word);
                }
            }
        }
        size = myVocabularyList.size();
        Log.d(AppConstants.LOG_TAG, "myVocabularyList.size() = " + size);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_words, container, false);

        tvTask = (TextView) view.findViewById(R.id.tvTask);
        etAnswer = (EditText) view.findViewById(R.id.etAnswer);
        btnTest = (Button) view.findViewById(R.id.btnCheck);
        btnTest.setOnClickListener(this);
        btnHelp = (Button) view.findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);
        int temp = getRandomNumber();
        word = myVocabularyList.get(temp);
        tvTask.setText(word.getRussianWord());
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheck:
                String answer = etAnswer.getText().toString();
                checkAnswer(answer, word.getEnglishWord());
                break;
            case R.id.btnHelp:
                Toast.makeText(getActivity(), word.getEnglishWord(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void checkAnswer(String answer, String englishWord) {
        if(answer.equals(englishWord)){
            word = myVocabularyList.get(getRandomNumber());
            tvTask.setText(word.getRussianWord());
            etAnswer.setText("");
            Toast.makeText(getActivity(), "Вы ответили правильно! Поздравляю!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Вы дали неправильный ответ! Попробуйте ещё раз.", Toast.LENGTH_SHORT).show();
        }
    }

    private int getRandomNumber() {
        int a = 0;
        int random_number = a + (int) (Math.random() * size);
        Log.d(AppConstants.LOG_TAG, "random_number = " + random_number);
        return random_number;
    }
}
