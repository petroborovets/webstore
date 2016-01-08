package ua.net.aspebo.service.impl;

import ua.net.aspebo.service.TransactionService;
import ua.net.aspebo.domain.Transaction;
import ua.net.aspebo.repository.TransactionRepository;
import ua.net.aspebo.repository.search.TransactionSearchRepository;
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
 * Service Implementation for managing Transaction.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    
    @Inject
    private TransactionRepository transactionRepository;
    
    @Inject
    private TransactionSearchRepository transactionSearchRepository;
    
    /**
     * Save a transaction.
     * @return the persisted entity
     */
    public Transaction save(Transaction transaction) {
        log.debug("Request to save Transaction : {}", transaction);
        Transaction result = transactionRepository.save(transaction);
        transactionSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the transactions.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Transaction> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        Page<Transaction> result = transactionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one transaction by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Transaction findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        Transaction transaction = transactionRepository.findOne(id);
        return transaction;
    }

    /**
     *  delete the  transaction by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.delete(id);
        transactionSearchRepository.delete(id);
    }

    /**
     * search for the transaction corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Transaction> search(String query) {
        
        log.debug("REST request to search Transactions for query {}", query);
        return StreamSupport
            .stream(transactionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
