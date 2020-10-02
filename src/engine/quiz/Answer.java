package engine.quiz;

import java.util.Collections;
import java.util.List;

public class Answer {
    private List<Integer> answer;

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
        Collections.sort(this.answer);
    }
}