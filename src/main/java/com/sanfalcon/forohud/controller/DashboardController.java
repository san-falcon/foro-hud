package com.sanfalcon.forohud.controller;

import com.sanfalcon.forohud.domain.topico.DatosListaTopico;
import com.sanfalcon.forohud.domain.topico.TopicoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    private TopicoRespository topicoRespository;

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(
            @RequestParam(required = false) String filtro,
            @PageableDefault(size = 15) Pageable pageable
    ) {

        if (filtro != null && !filtro.isBlank()) {
            final var listTopicos = this.topicoRespository
                    .findByTituloContainingIgnoreCaseOrMensajeContainingIgnoreCase(
                            filtro,
                            filtro,
                            pageable)
                    .map(DatosListaTopico::new);

            return ResponseEntity.ok(listTopicos);
        }

        return ResponseEntity.ok(this.topicoRespository.findAll(pageable).map(DatosListaTopico::new));

    }
}
