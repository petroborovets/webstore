package ua.net.aspebo.service;

import ua.net.aspebo.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Transaction.
 */
public interface TransactionService {

    /**
     * Save a transaction.
     * @return the persisted entity
     */
    public Transaction save(Transaction transaction);

    /**
     *  get all the transactions.
     *  @return the list of entities
     */
    public Page<Transaction> findAll(Pageable pageable);

    /**
     *  get the "id" transaction.
     *  @return the entity
     */
    public Transaction findOne(Long id);

    /**
     *  delete the "id" transaction.
     */
    public void delete(Long id);

    /**
     * search for the transaction corresponding
     * to the query.
     */
    public List<Transaction> search(String query);
}
