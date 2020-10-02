package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepository extends PagingAndSortingRepository<SolvedQuiz, Long> {
    Page<SolvedQuiz> findAllBySolversEmail(String solversEmail, Pageable pageable);
}
