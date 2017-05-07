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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import s.tertj.myvocabulary.AppConstants;
import s.tertj.myvocabulary.DB.DB;
import s.tertj.myvocabulary.R;
import s.tertj.myvocabulary.interfaces.Link;

public class AddWordFragment extends Fragment implements View.OnClickListener {

    private EditText etEnglishWord, etRussionWord, etWordTag;
    private Button btnAddWord, btnGetTranslate;
    private IOnMyAddWordFragmentClickListener addWordClickListener;
    private String text;
    private Gson gson = new GsonBuilder().create();
    private DB db;
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(AppConstants.YANDEX_URL)
            .build();

    private Link intf = retrofit.create(Link.class);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addWordClickListener = (IOnMyAddWordFragmentClickListener) activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_word, container, false);

        etEnglishWord = (EditText) view.findViewById(R.id.etEnglishWord);
        etRussionWord = (EditText) view.findViewById(R.id.etRussionWord);
        etWordTag = (EditText) view.findViewById(R.id.etWordTag);

        btnAddWord = (Button) view.findViewById(R.id.btnAddWord);
        btnAddWord.setOnClickListener(this);
        btnGetTranslate = (Button) view.findViewById(R.id.btnGetTranslate);
        btnGetTranslate.setOnClickListener(this);
        db = new DB(getActivity());
        db.open();
        return view;
    }

    @Override
    public void onClick(View v) {
        String englishWord;
        String russianWord;
        String wordTag;
        switch (v.getId()) {
            case R.id.btnAddWord:
                englishWord = etEnglishWord.getText().toString();
                russianWord = etRussionWord.getText().toString();
                wordTag = etWordTag.getText().toString();
                db.addRec(englishWord, russianWord, wordTag);
                addWordClickListener.onWordWasAdded();
                break;

            case R.id.btnGetTranslate:

                englishWord = etEnglishWord.getText().toString();
                russianWord = etRussionWord.getText().toString();
                wordTag = etWordTag.getText().toString();

                if (!englishWord.equals("")) {
                    Map<String, String> mapJson = new HashMap<String, String>();
                    mapJson.put("key", AppConstants.YANDEX_KEY);
                    mapJson.put("lang", "en-ru");
                    mapJson.put("text", englishWord);
                    Call<Object> call = intf.translate(mapJson);
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Response<Object> response, Retrofit retrofit) {
                            String jsonResp = response.body().toString();
                            Log.d(AppConstants.LOG_TAG, "jsonResp = " + jsonResp);
                            Map<String, String> mapResponse = gson.fromJson(jsonResp, Map.class);
                            for (Map.Entry e : mapResponse.entrySet()) {
                                if (e.getKey().equals("text")) {
                                    text = e.getValue().toString();

                                    getResponse(text);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getActivity(), "Введите слово." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                break;
            default:
                break;
        }

    }

    private void getResponse(String text) {
        char[] resp = text.toCharArray();
        int respLength = resp.length - 1;
        Log.d(AppConstants.LOG_TAG, "respLength =" + respLength);
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 1; i < respLength; i++) {
            strBuffer.append(resp[i]);
            etRussionWord.setText(strBuffer.toString());
        }


    }

    public interface IOnMyAddWordFragmentClickListener {
        void onWordWasAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.close();
    }

}
