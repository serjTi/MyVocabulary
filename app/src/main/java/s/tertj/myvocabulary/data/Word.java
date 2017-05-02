package s.tertj.myvocabulary.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {


    private String id;
    private String englishWord;
    private String russianWord;
//    private String tag;

public void setId(String id) {
    this.id = id;
}

    public String getId() {

        return id;
    }
    public String getEnglishWord() {
        return englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }

//    public String getTag() {
//        return tag;
//    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setRussianWord(String russianWord) {
        this.russianWord = russianWord;
    }

//    public void setTag(String tag) {
//        this.tag = tag;
//    }

    public Word(){
        this.id = "";
        this.englishWord = "";
        this.russianWord = "";
    }
    public Word(Parcel in){
        String[] data = new String[3];
        in.readStringArray(data);
        this.id = data[0];
        this.englishWord = data[1];
        this.russianWord = data[2];

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.id, this.englishWord, this.russianWord});
    }
    public static final Parcelable.Creator<Word> CREATOR= new Parcelable.Creator<Word>() {

        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
