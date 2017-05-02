package s.tertj.myvocabulary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import s.tertj.myvocabulary.MainActivity;
import s.tertj.myvocabulary.R;
import s.tertj.myvocabulary.data.Word;

public class MyVocabularyAdapter extends BaseAdapter {

    private List<Word> myVocabularyList;
    Context context;





    public MyVocabularyAdapter(Context c, List<Word> myVocabularyList) {
        this.myVocabularyList = myVocabularyList;
        this.context = c;
    }

    static class ViewHolder {
        public TextView tvEnglishWord, tvRussianWord;
        public ImageButton ibDeleteRow;
    }

    @Override
    public int getCount() {
        return myVocabularyList.size();
    }

    @Override
    public Word getItem(int position) {
        return myVocabularyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final Word word = myVocabularyList.get(position);
        // reuse views
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vocabulary_item, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.tvEnglishWord = (TextView) convertView.findViewById(R.id.tvEnglishWord);
            viewHolder.tvRussianWord = (TextView) convertView.findViewById(R.id.tvRussianWord);
            viewHolder.ibDeleteRow = (ImageButton) convertView.findViewById(R.id.ibDeleteRow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // fill data
        viewHolder.tvEnglishWord.setText(word.getEnglishWord());
        viewHolder.tvRussianWord.setText(word.getRussianWord());
        viewHolder.ibDeleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.deleteDBRow(word.getId());
            }
        });
        return convertView;
    }
}
