package jpabook_jpashop.Repository;

import jpabook_jpashop.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
