package diplom.service;

import diplom.model.User;
import diplom.model.enums.Rating;
import diplom.model.Post;
import diplom.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    //=============================================================================

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
}
