package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.LinkedList;

import s.tertj.myvocabulary.R;
import s.tertj.myvocabulary.adapters.MyVocabularyAdapter;
import s.tertj.myvocabulary.data.Word;

public class MyVocabularyFragment extends Fragment  {

    private ListView lvMyVocabulary;
    private MyVocabularyAdapter myVocabularyAdapter;
    private LinkedList<Word> myVocabularyList = new LinkedList<Word>();

    public static MyVocabularyFragment newInstance(LinkedList<Word> vocabularyLinkedList) {
        MyVocabularyFragment catalogFragment = new MyVocabularyFragment();
        Bundle args = new Bundle();
        for (int i = 0; i < vocabularyLinkedList.size(); i++) {
            args.putParcelable("Word " + i, vocabularyLinkedList.get(i));
        }
        catalogFragment.setArguments(args);
        return catalogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_vocabulary, container, false);
        lvMyVocabulary = (ListView) view.findViewById(R.id.lvMyVocabulary);
        if (getArguments() != null) {
            for (int i = 0; i < getArguments().size(); i++) {
                Word word = getArguments().getParcelable("Word " + i);
                if (word != null) {
                    myVocabularyList.add(word);
                }
            }

        }
        myVocabularyAdapter = new MyVocabularyAdapter(getActivity(), myVocabularyList);
        lvMyVocabulary.setAdapter(myVocabularyAdapter);
        return view;
    }


}
