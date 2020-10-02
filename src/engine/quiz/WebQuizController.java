package engine.quiz;

import engine.authorization.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/quizzes")
public class WebQuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SolvedQuizRepository solvedQuizRepository;

    @GetMapping(path = "/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            return quiz.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no such quiz");
        }
    }

    @GetMapping
    public Page<Quiz> getAllQuizzes(
            @RequestParam(name = "page", defaultValue = "0") int page) {
        return quizRepository.findAll(PageRequest.of(page, 10));
    }

    @GetMapping(path = "/completed")
    public Page<SolvedQuiz> getSolvedQuizzes(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @AuthenticationPrincipal User user) {
        return solvedQuizRepository.findAllBySolversEmail(user.getEmail(),
                PageRequest.of(page, 10, Sort.by("completedAt").descending()));
    }

    @PostMapping(consumes = "application/json")
    public Quiz createWebQuiz(@Valid @RequestBody CustomQuiz customQuiz,
                              @AuthenticationPrincipal User user) {
        Quiz quiz = new Quiz(customQuiz, user.getEmail());
        quizRepository.save(quiz);
//        solvedQuizRepository.save(new SolvedQuiz(quiz, user));
        return quiz;
    }

    @PostMapping(path = "/{id}/solve", consumes = "application/json")
    public Result postAnswer(@PathVariable long id,
                             @RequestBody Answer answer,
                             @AuthenticationPrincipal User user) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            if (answer.getAnswer().equals(quiz.get().getAnswer())) {
                solvedQuizRepository.save(new SolvedQuiz(quiz.get(), user));
                return Result.RIGHT;
            }
            return Result.WRONG;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no such quiz");
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteQuiz(@PathVariable long id, @AuthenticationPrincipal User user) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        if (quiz.get().getAuthor().equals(user.getEmail())) {
            quizRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Quiz was deleted");
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Quiz belongs another user");
        }
    }
}
