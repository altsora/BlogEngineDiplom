package diplom.repository;

import diplom.model.Post;
import diplom.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findTagsByNameStartingWith(String query);

}
