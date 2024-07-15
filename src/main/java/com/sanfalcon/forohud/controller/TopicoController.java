package com.sanfalcon.forohud.controller;

import com.sanfalcon.forohud.domain.topico.*;
import com.sanfalcon.forohud.infra.errores.ValidacionDeIntegridad;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRespository topicoRespository;


    @PostMapping
    public ResponseEntity crearTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        TopicoEntity topicoEntity = TopicoEntity.builder()
                .titulo(datos.titulo())
                .mensaje(datos.mensaje())
                .autor(datos.autor())
                .curso(datos.curso())
                .estado(Estado.PENDIENTE)
                .fecha(LocalDateTime.now())
                .build();
        topicoRespository.save(topicoEntity);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(topicoEntity.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listaTopicos(@PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(topicoRespository.findAll(pageable).map(DatosListaTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalleTopico(@PathVariable Long id, HttpServletRequest request) {
        Optional<TopicoEntity> topico = topicoRespository.findById(id);
        if (!topico.isPresent()) {
            throw new ValidacionDeIntegridad("Not Found", "No se encontro el registro", request, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizaTopico(@RequestBody @Valid DatosActualizarTopico datos, @PathVariable Long id, HttpServletRequest request) {
        Optional<TopicoEntity> topico = topicoRespository.findById(id);
        if (!topico.isPresent()) {
            throw new ValidacionDeIntegridad("Not Found", "No se encontro el registro", request, HttpStatus.NOT_FOUND);
        }
        topico.get().actualizaDatos(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
    }

    @PutMapping("/estado")
    @Transactional
    public ResponseEntity actualizaTopicoAtentido(@RequestBody @Valid DatosActualizarTopicoAtendido datos, HttpServletRequest request) {
        Optional<TopicoEntity> topico = topicoRespository.findById(datos.id());
        if (!topico.isPresent()) {
            throw new ValidacionDeIntegridad("Not Found", "No se encontro el registro", request, HttpStatus.NOT_FOUND);
        }
        topico.get().actualizaAtentido(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id, HttpServletRequest request) {
        Optional<TopicoEntity> topico = topicoRespository.findById(id);
        if (!topico.isPresent()) {
            throw new ValidacionDeIntegridad("Not Found", "No se encontro el registro", request, HttpStatus.NOT_FOUND);
        }
        topicoRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
