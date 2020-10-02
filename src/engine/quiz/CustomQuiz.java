package engine.quiz;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

public class CustomQuiz {
    @NotBlank(message = "Quiz has blank title field")
    private String title;

    @NotBlank(message = "Quiz has blank text field")
    private String text;

    @NotEmpty(message = "Quiz options can not be empty")
    @Size(min = 2, message = "Quiz must have 2 options at least")
    private List<String> options;

    private List<Integer> answer = List.of();

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
        if (answer != null) {
            this.answer = answer;
            Collections.sort(this.answer);
        }
    }
}
