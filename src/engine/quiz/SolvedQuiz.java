package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.authorization.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SolvedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long quizId;


    private long id;

    @JsonIgnore
    private String title;
    @JsonIgnore
    private String text;

    @JsonIgnore
    @ElementCollection
    private List<String> options;

    @JsonIgnore
    @ElementCollection
    private List<Integer> answer;

    @JsonIgnore
    private String author;

    private LocalDateTime completedAt;

    @JsonIgnore
    private String solversEmail;

    public SolvedQuiz(Quiz quiz, User user) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = new ArrayList<>(quiz.getOptions());
        this.answer = new ArrayList<>(quiz.getAnswer());
        this.author = quiz.getAuthor();
        this.completedAt = LocalDateTime.now();
        this.solversEmail = user.getEmail();
    }

    public SolvedQuiz() {
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
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

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime time) {
        this.completedAt = time;
    }

    public String getSolversEmail() {
        return solversEmail;
    }

    public void setSolversEmail(String solversEmail) {
        this.solversEmail = solversEmail;
    }
}