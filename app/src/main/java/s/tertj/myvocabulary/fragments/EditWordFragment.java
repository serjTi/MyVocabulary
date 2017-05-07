package s.tertj.myvocabulary.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import s.tertj.myvocabulary.DB.DB;
import s.tertj.myvocabulary.R;

public class EditWordFragment extends Fragment {


    private long id;
    private DB db;
    private EditText etEnglishEdit, etRussianEdit, etTagEdit;

    IOnMyEditFragmentClickListener iOnMyEditFragmentClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iOnMyEditFragmentClickListener = (IOnMyEditFragmentClickListener) activity;
    }

    public interface IOnMyEditFragmentClickListener {
        void editIsFinished();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_is_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String english_edit = etEnglishEdit.getText().toString();
        String russian_edit = etRussianEdit.getText().toString();
        String tag_edit = etTagEdit.getText().toString();
        db.updateRec(id, english_edit, russian_edit, tag_edit);
        Toast.makeText(getActivity(), "Ваши данные обновлены", Toast.LENGTH_SHORT).show();
        iOnMyEditFragmentClickListener.editIsFinished();
        return super.onOptionsItemSelected(item);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        Bundle args = getArguments();
        if (args != null)
            id = args.getLong("_id");
        etEnglishEdit = (EditText) view.findViewById(R.id.etEnglishEdit);
        etRussianEdit = (EditText) view.findViewById(R.id.etRussianEdit);
        etTagEdit = (EditText) view.findViewById(R.id.etTagEdit);
        db = new DB(getActivity());
        db.open();
        Cursor cursor = db.getCurrentData(String.valueOf(id));
        initViews(cursor);
        return view;
    }

    private void initViews(Cursor cursor) {
        String englishWord = "";
        String russianWord = "";
        String wordTag = "";
        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex(DB.COLUMN_EW);
            int typeColIndex = cursor.getColumnIndex(DB.COLUMN_RW);
            int colorColIndex = cursor.getColumnIndex(DB.COLUMN_WORD_TAG);
            do {
                englishWord = cursor.getString(nameColIndex);
                russianWord = cursor.getString(typeColIndex);
                wordTag = cursor.getString(colorColIndex);
            } while (cursor.moveToNext());
        }
        etEnglishEdit.setText(englishWord);
        etRussianEdit.setText(russianWord);
        etTagEdit.setText(wordTag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.close();
    }
}