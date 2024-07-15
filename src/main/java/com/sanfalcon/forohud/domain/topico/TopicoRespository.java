package com.sanfalcon.forohud.domain.topico;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRespository extends JpaRepository<TopicoEntity, Long> {
}
