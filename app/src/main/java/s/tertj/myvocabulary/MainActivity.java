package s.tertj.myvocabulary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;

import s.tertj.myvocabulary.data.Word;
import s.tertj.myvocabulary.fragments.AddWordFragment;
import s.tertj.myvocabulary.fragments.LearnWordsFragment;
import s.tertj.myvocabulary.fragments.MyVocabularyFragment;
import s.tertj.myvocabulary.fragments.StartFragment;

public class MainActivity extends AppCompatActivity implements
        StartFragment.IOnMyStartFragmentClickListener, AddWordFragment.IOnMyAddWordClickListener {

   static DBHelper dbHelper;

    //fragments
    private FragmentTransaction fTrans;
    private StartFragment startFragment;
    private AddWordFragment addWordFragment;
    private LearnWordsFragment learnWordsFragment;
    private MyVocabularyFragment myVocabularyFragment;


    private LinkedList<Word> myVocabularyList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startFragment = new StartFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, startFragment);
        fTrans.addToBackStack("StartFragment");
        fTrans.commit();
        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
        getDatabase();
    }

    private void getDatabase() {
        myVocabularyList = new LinkedList<Word>();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(AppConstants.LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(AppConstants.ID);
            int engWColIndex = c.getColumnIndex(AppConstants.ENGLISH_WORD);
            int rusWColIndex = c.getColumnIndex(AppConstants.RUSSIAN_WORD);

            do {
                Word word = new Word();
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(AppConstants.LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", " + AppConstants.ENGLISH_WORD + " = " + c.getString(engWColIndex) +
                                ", " + AppConstants.RUSSIAN_WORD + " = " + c.getString(rusWColIndex));
                word.setId(String.valueOf(c.getInt(idColIndex)));
                word.setEnglishWord(c.getString(engWColIndex));
                word.setRussianWord(c.getString(rusWColIndex));
                myVocabularyList.add(word);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(AppConstants.LOG_TAG, "0 rows");
        c.close();
        dbHelper.close();
    }

    @Override
    public void onAddFragmentButtonClick() {
        addWordFragment = new AddWordFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, addWordFragment);
        fTrans.addToBackStack("AddWordFragment");
        fTrans.commit();
    }

    @Override
    public void onMyVocabularyButtonClick() {
        myVocabularyList = null;
        getDatabase();
        if (myVocabularyList.size() > 0) {
            myVocabularyFragment = MyVocabularyFragment.newInstance(myVocabularyList);
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.replace(R.id.flFragmentContainer, myVocabularyFragment);
            fTrans.addToBackStack("MyVocabularyFragment");
            fTrans.commit();
        } else {
            Toast.makeText(this, "В Вашем словаре нет слов", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLearnWordsButtonClick() {
        if (myVocabularyList.size() > 0) {
            learnWordsFragment = LearnWordsFragment.newInstance(myVocabularyList);
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.replace(R.id.flFragmentContainer, learnWordsFragment);
            fTrans.addToBackStack("LearnWordsFragment");
            fTrans.commit();
        } else {
            Toast.makeText(this, "У Вас нет слов для изучения", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddWordClick(String englishWord, String russianWord) {
        Log.d(AppConstants.LOG_TAG, "englishWord = " + englishWord + "; russianWord = " + russianWord);
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(AppConstants.LOG_TAG, "--- Insert in mytable: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение
        cv.put(AppConstants.ENGLISH_WORD, englishWord);
        cv.put(AppConstants.RUSSIAN_WORD, russianWord);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("mytable", null, cv);
        Log.d(AppConstants.LOG_TAG, "row inserted, ID = " + rowID);
        // закрываем подключение к БД
        dbHelper.close();
        getDatabase();
    }

    public static void deleteDBRow(String id) {
    int rowID = Integer.parseInt(id);
        Log.d(AppConstants.LOG_TAG, "--- Delete from mytable: ---");
        // удаляем по id
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delCount = db.delete("mytable", "id = " + rowID, null);
        Log.d(AppConstants.LOG_TAG, "deleted rows count = " + delCount);
        dbHelper.close();
    }

}