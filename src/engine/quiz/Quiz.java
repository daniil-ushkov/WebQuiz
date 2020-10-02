package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String text;

    @ElementCollection
    private List<String> options;

    @JsonIgnore
    @ElementCollection
    private List<Integer> answer;

    @JsonIgnore
    private String author;

    private void checkAnswer() {
        boolean[] number = new boolean[options.size()];
        for (Integer singleAnswer : answer) {
            if (singleAnswer < 0 || singleAnswer >= number.length || number[singleAnswer]) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "illegal answer format");
            }
            number[singleAnswer] = true;
        }
    }

    public Quiz(CustomQuiz customQuiz, String author) {
        this.title = customQuiz.getTitle();
        this.text = customQuiz.getText();
        this.options = customQuiz.getOptions();
        this.answer = customQuiz.getAnswer();
        checkAnswer();
        this.author = author;
    }

    public Quiz() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}