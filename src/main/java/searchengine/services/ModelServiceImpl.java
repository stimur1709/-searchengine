package searchengine.services;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class ModelServiceImpl<E, K, R extends JpaRepository<E, K>> implements ModelService<E, K> {

    protected final R repository;

    protected ModelServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E save(E e) {
        return repository.save(e);
    }

    @Override
    public void deleteById(K k) {
        repository.deleteById(k);
    }

    @Override
    public void saveAll(List<E> list) {
        repository.saveAllAndFlush(list);
    }

}
