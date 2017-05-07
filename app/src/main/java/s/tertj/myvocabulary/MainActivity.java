package s.tertj.myvocabulary;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import s.tertj.myvocabulary.fragments.AddWordFragment;
import s.tertj.myvocabulary.fragments.EditWordFragment;
import s.tertj.myvocabulary.fragments.LearnWordsFragment;
import s.tertj.myvocabulary.fragments.MyVocabularyFragment;
import s.tertj.myvocabulary.fragments.StartFragment;

public class MainActivity extends AppCompatActivity implements
        StartFragment.IOnMyStartFragmentClickListener, AddWordFragment.IOnMyAddWordFragmentClickListener,
MyVocabularyFragment.IOnMyVocabularyFragmentClickListener, EditWordFragment.IOnMyEditFragmentClickListener{
    private FragmentTransaction fTrans;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartFragment startFragment = new StartFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, startFragment);
        fTrans.addToBackStack("StartFragment");
        fTrans.commit();
    }


    @Override
    public void onAddFragmentButtonClick() {
        AddWordFragment addWordFragment = new AddWordFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, addWordFragment);
//        fTrans.addToBackStack("AddWordFragment");
        fTrans.commit();
    }

    @Override
    public void onMyVocabularyButtonClick() {
        MyVocabularyFragment myVocabularyFragment = new MyVocabularyFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, myVocabularyFragment);
        fTrans.addToBackStack("MyVocabularyFragment");
        fTrans.commit();
    }

    @Override
    public void onLearnWordsButtonClick() {
            LearnWordsFragment learnWordsFragment = new LearnWordsFragment();
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.replace(R.id.flFragmentContainer, learnWordsFragment);
            fTrans.addToBackStack("LearnWordsFragment");
            fTrans.commit();
    }

    // MyVocabularyFragment callback's
    @Override
    public void onWordClickInVocabulary(long id) {

    }

    @Override
    public void onWordEditButtonClick(long id) {
        EditWordFragment editWordFragment = new EditWordFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putLong("_id", id);
        editWordFragment.setArguments(args);
        fTrans.replace(R.id.flFragmentContainer, editWordFragment);
//        fTrans.addToBackStack("EditWordFragment");
        fTrans.commit();
    }


    @Override
    public void onShowAddWordFragment() {
        AddWordFragment addWordFragment = new AddWordFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, addWordFragment);
//        fTrans.addToBackStack("AddWordFragment");
        fTrans.commit();
    }

    @Override
    public void onWordDeleteButtonClick() {
        MyVocabularyFragment myVocabularyFragment = new MyVocabularyFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, myVocabularyFragment);
        fTrans.addToBackStack("MyVocabularyFragment");
        fTrans.commit();
    }

    // AddWordFragment callback's
    @Override
    public void onWordWasAdded() {
        MyVocabularyFragment myVocabularyFragment = new MyVocabularyFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, myVocabularyFragment);
        fTrans.addToBackStack("MyVocabularyFragment");
        fTrans.commit();
    }

    @Override
    public void editIsFinished() {
        MyVocabularyFragment myVocabularyFragment = new MyVocabularyFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, myVocabularyFragment);
        fTrans.addToBackStack("MyVocabularyFragment");
        fTrans.commit();
    }

}