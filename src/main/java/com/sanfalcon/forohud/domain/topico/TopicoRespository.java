package com.sanfalcon.forohud.domain.topico;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRespository extends JpaRepository<TopicoEntity, Long> {

    Page<TopicoEntity> findByTituloContainingIgnoreCaseOrMensajeContainingIgnoreCase(
            String titulo,
            String mensaje,
            Pageable pageable
    );
}
