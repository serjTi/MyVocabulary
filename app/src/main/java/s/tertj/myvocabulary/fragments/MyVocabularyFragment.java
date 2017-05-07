package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import s.tertj.myvocabulary.AppConstants;
import s.tertj.myvocabulary.DB.DB;
import s.tertj.myvocabulary.DB.MyCursorLoader;
import s.tertj.myvocabulary.R;

public class MyVocabularyFragment extends Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvMyVocabulary;
    private Button btnAddItem;
    private DB db;
    private SimpleCursorAdapter scAdapter;
    private long current_id;
    private ActionMode myMode = null;
    private IOnMyVocabularyFragmentClickListener iOnMyVocabularyFragmentClickListener;
private boolean connect_to_db = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iOnMyVocabularyFragmentClickListener = (IOnMyVocabularyFragmentClickListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_vocabulary, container, false);

        btnAddItem = (Button) view.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        db = new DB(getActivity());
        db.open();
        connect_to_db = true;
        String[] from = new String[]{DB.COLUMN_EW, DB.COLUMN_RW};
        int[] to = new int[]{R.id.tvEnglishWord, R.id.tvRussianWord};

        scAdapter = new SimpleCursorAdapter(getActivity(), R.layout.vocabulary_item, null, from, to, 0);

        lvMyVocabulary = (ListView) view.findViewById(R.id.lvMyVocabulary);
        lvMyVocabulary.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvMyVocabulary.setMultiChoiceModeListener(new ModeCallback());
        lvMyVocabulary.setAdapter(scAdapter);
        lvMyVocabulary.setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
            db.close();
        if (myMode != null) {
            myMode.finish();
        }
        Log.d(AppConstants.LOG_TAG, "MyVocabularyFragment onDestroyView");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItem:
                iOnMyVocabularyFragmentClickListener.onShowAddWordFragment();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iOnMyVocabularyFragmentClickListener.onWordClickInVocabulary(id);

    }


    public interface IOnMyVocabularyFragmentClickListener {
        void onWordClickInVocabulary(long id);

        void onWordEditButtonClick(long id);

        void onShowAddWordFragment();

        void onWordDeleteButtonClick();
    }

    private class ModeCallback implements ListView.MultiChoiceModeListener {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            current_id = id;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_edit, menu);
            myMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mi_delete:
                    db.delRec(current_id);
                    iOnMyVocabularyFragmentClickListener.onWordDeleteButtonClick();
                    break;
                case R.id.mi_edit:
                    iOnMyVocabularyFragmentClickListener.onWordEditButtonClick(current_id);
                    break;
                default:
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(AppConstants.LOG_TAG, "MyVocabularyFragment onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(AppConstants.LOG_TAG, "MyVocabularyFragment onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(AppConstants.LOG_TAG, "MyVocabularyFragment onDetach");
    }
}
