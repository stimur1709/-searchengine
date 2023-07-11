package searchengine.services;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class ModelServiceImpl<E, K, R extends JpaRepository<E, K>> implements ModelService<E, K> {

    protected final R repository;

    protected ModelServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public E findByKey(K k) {
        return repository.findById(k)
                .orElseThrow();
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(E e) {
        repository.save(e);
    }

    @Override
    public boolean contains(K k) {
        return repository.findById(k).isPresent();
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
