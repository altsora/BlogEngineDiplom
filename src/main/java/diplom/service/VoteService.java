package diplom.service;

import diplom.enums.Rating;
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
}
