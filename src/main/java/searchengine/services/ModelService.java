package searchengine.services;

import java.util.List;

public interface ModelService<E, K> {

    List<E> findAll();

    E save(E e);

    void deleteById(K k);

    void saveAll(List<E> list);
}
