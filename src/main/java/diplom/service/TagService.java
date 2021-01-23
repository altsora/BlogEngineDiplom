package diplom.service;

import diplom.model.Tag;
import diplom.repository.TagRepository;
import diplom.response.ResultResponse;
import diplom.response.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static diplom.model.enums.ActivityStatus.ACTIVE;
import static diplom.model.enums.ModerationStatus.ACCEPTED;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final PostService postService;

    //------------------------------------------------------------------------------------------------------------------

    public ResultResponse getTagList(String query) {
        double minNormalizedWeight = 0.3d;  // Для отсечения редких тегов, т.к. на фронте их не разглядеть
        List<Tag> tagListRep = query == null ?
                tagRepository.findAll() :
                tagRepository.findTagsByNameStartingWith(query);

        List<Double> weights = new ArrayList<>();
        int totalCountPosts = postService.countPosts(ACTIVE, ACCEPTED);
        double maxWeight = -1;
        double weight;
        for (Tag tagRep : tagListRep) {
            int countPostsByTag = postService.countPostsByTag(tagRep.getName());
            weight = (double) countPostsByTag / totalCountPosts;
            weights.add(weight);
            if (weight > maxWeight) {
                maxWeight = weight;
            }
        }

        List<TagResponse> tags = new ArrayList<>();
        int size = tagListRep.size();
        for (int i = 0; i < size; i++) {
            String tagName = tagListRep.get(i).getName();
            double normalizedWeight = weights.get(i) / maxWeight;
            if (Double.compare(normalizedWeight, minNormalizedWeight) >= 0) {
                TagResponse tag = TagResponse.builder().name(tagName).weight(normalizedWeight).build();
                tags.add(tag);
            }
        }

        return ResultResponse.builder().tags(tags).build();
    }

    public List<String> getTagsByPost() {
        return null;
    }

}
