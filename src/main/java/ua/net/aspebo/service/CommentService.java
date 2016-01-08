package ua.net.aspebo.service;

import ua.net.aspebo.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {

    /**
     * Save a comment.
     * @return the persisted entity
     */
    public Comment save(Comment comment);

    /**
     *  get all the comments.
     *  @return the list of entities
     */
    public Page<Comment> findAll(Pageable pageable);

    /**
     *  get the "id" comment.
     *  @return the entity
     */
    public Comment findOne(Long id);

    /**
     *  delete the "id" comment.
     */
    public void delete(Long id);

    /**
     * search for the comment corresponding
     * to the query.
     */
    public List<Comment> search(String query);
}
