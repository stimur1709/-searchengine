package searchengine.services;

import java.util.List;

public interface ModelService<E, K> {

    E findByKey(K k);

    List<E> findAll();

    void save(E e);

    boolean contains(K k);

    void deleteById(K k);

    void saveAll(List<E> list);
}
