package ua.net.aspebo.service.impl;

import ua.net.aspebo.service.CommentService;
import ua.net.aspebo.domain.Comment;
import ua.net.aspebo.repository.CommentRepository;
import ua.net.aspebo.repository.search.CommentSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    
    @Inject
    private CommentRepository commentRepository;
    
    @Inject
    private CommentSearchRepository commentSearchRepository;
    
    /**
     * Save a comment.
     * @return the persisted entity
     */
    public Comment save(Comment comment) {
        log.debug("Request to save Comment : {}", comment);
        Comment result = commentRepository.save(comment);
        commentSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the comments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Comment> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        Page<Comment> result = commentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one comment by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Comment findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRepository.findOne(id);
        return comment;
    }

    /**
     *  delete the  comment by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.delete(id);
        commentSearchRepository.delete(id);
    }

    /**
     * search for the comment corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Comment> search(String query) {
        
        log.debug("REST request to search Comments for query {}", query);
        return StreamSupport
            .stream(commentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
