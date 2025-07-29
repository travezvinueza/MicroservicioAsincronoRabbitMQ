package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.MovimientoDTO;
import com.develop.cuentamovimientos.service.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movimientos")
@AllArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDTO> crear(@RequestBody MovimientoDTO movimientoDTO){
        return new ResponseEntity<>(movimientoService.crear(movimientoDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MovimientoDTO>> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<MovimientoDTO> movimientoDTOPage = movimientoService.listar(pageable);
        return new ResponseEntity<>(movimientoDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(movimientoService.obtenerPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        movimientoService.eliminarPorId(id);
    }

    @PutMapping
    public ResponseEntity<MovimientoDTO> actualizar(@RequestBody MovimientoDTO movimientoDTO){
        return new ResponseEntity<>(movimientoService.actualizar(movimientoDTO), HttpStatus.OK);
    }

}