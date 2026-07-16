package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.Sushi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;

public interface SushiRepository extends JpaRepository<Sushi, Long> {
    @Override
    @EntityGraph(attributePaths = {"ingredients"})
    Page<Sushi> findAll(Pageable pageable);
}
