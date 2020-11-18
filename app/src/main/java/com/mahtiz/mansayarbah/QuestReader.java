package com.mahtiz.mansayarbah;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestReader {
CompActivity activity;
public QuestReader(CompActivity activity){
    this.activity=activity;
}
public List<Question>getQuetions(String filename) throws IOException {
InputStream is=activity.getAssets().open(filename);
    Scanner s=new Scanner(is);
    List<Question> questions=new ArrayList<>();
    while(s.hasNextLine()){
        String qtext=s.nextLine();
        List<String>choices=new ArrayList<>();
        for(int i=0;i<4;i++){
            choices.add(s.nextLine());
           // Log.i("ttt", String.valueOf(choices.add(s.nextLine())));
        }
        String correctAnswer=s.nextLine();
        String photo=s.nextLine();
        Question q=new Question();
        q.setQuestionTest(qtext);
        q.setChoices(choices);
        q.setCorrectAnswer(correctAnswer);
        q.setPhoto(photo);
        questions.add(q);

    }
return questions;
    }
}
