package diplom.service;

import diplom.model.Post;
import diplom.model.User;
import diplom.model.Vote;
import diplom.model.enums.Rating;
import diplom.repository.PostRepository;
import diplom.repository.VoteRepository;
import diplom.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final PostRepository postRepository;

    //------------------------------------------------------------------------------------------------------------------

    public ResultResponse put(Rating rating, long postId) {
        User user = authService.getCurrentUser();
        Post post = postRepository.getOne(postId);
        Optional<Vote> voteOptional = voteRepository.findByUserAndPost(user, post);
        if (voteOptional.isEmpty()) {
            log.info("Успешно поставлен {}", rating);
            voteRepository.saveAndFlush(new Vote(user, post, rating));
            return ResultResponse.builder().result(true).build();
        }
        Vote vote = voteOptional.get();
        if (vote.getValue() != rating) {
            log.info("Был {}, ставим {}", vote.getValue(), rating);
            vote.replace(rating);
            voteRepository.saveAndFlush(vote);
            return ResultResponse.builder().result(true).build();
        }
        log.info("Удаляем существующий {}", vote.getValue());
        voteRepository.delete(vote);
        return ResultResponse.builder().result(false).build();
    }

    //------------------------------------------------------------------------------------------------------------------

    public int getCountLikesByPost(Post post) {
        return voteRepository.countByPostAndValue(post, Rating.LIKE);
    }

    public int getCountDislikeByPost(Post post) {
        return voteRepository.countByPostAndValue(post, Rating.DISLIKE);
    }

    public int getLikesCountByUser(User user) {
        return voteRepository.countByUserAndRating(user, Rating.LIKE);
    }

    public int getCountDislikesByUser(User user) {
        return voteRepository.countByUserAndRating(user, Rating.DISLIKE);
    }

    public int getLikesCount() {
        return voteRepository.countVotesByValue(Rating.LIKE);
    }

    public int getDislikesCount() {
        return voteRepository.countVotesByValue(Rating.DISLIKE);
    }

    //------------------------------------------------------------------------------------------------------------------
}
