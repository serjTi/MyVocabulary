package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import s.tertj.myvocabulary.AppConstants;
import s.tertj.myvocabulary.DB.DB;
import s.tertj.myvocabulary.DB.MyCursorLoader;
import s.tertj.myvocabulary.R;

public class LearnWordsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private int size;
    private Button btnTest, btnHelp;
    private TextView tvTask;
    private EditText etAnswer;
    private DB db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_words, container, false);

        db = new DB(getActivity());
        db.open();

        tvTask = (TextView) view.findViewById(R.id.tvTask);
        etAnswer = (EditText) view.findViewById(R.id.etAnswer);
        btnTest = (Button) view.findViewById(R.id.btnCheck);
        btnTest.setOnClickListener(this);
        btnHelp = (Button) view.findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);
        int temp = getRandomNumber();
        getLoaderManager().initLoader(0, null, this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheck:
                String answer = etAnswer.getText().toString();
                break;
            case R.id.btnHelp:
                break;
            default:
                break;
        }
    }

    private void checkAnswer(String answer, String englishWord) {

    }

    private int getRandomNumber() {
        int a = 0;
        int random_number = a + (int) (Math.random() * size);
        Log.d(AppConstants.LOG_TAG, "random_number = " + random_number);
        return random_number;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        scAdapter.swapCursor(cursor);
        getData(cursor);
    }

    private void getData(Cursor cursor) {
        Cursor myDataCursor = cursor;
        size = cursor.getCount();
        Toast.makeText(getActivity(), size, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
