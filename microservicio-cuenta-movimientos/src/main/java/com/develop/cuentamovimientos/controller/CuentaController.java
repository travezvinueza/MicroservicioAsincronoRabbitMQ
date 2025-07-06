package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.service.CuentaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas")
@AllArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaDTO> crear(@RequestBody @Valid CuentaDTO cuentaDTO){
        return new ResponseEntity<>(cuentaService.crear(cuentaDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CuentaDTO>> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(0, 5);
        Page<CuentaDTO> cuentaDTOPage = cuentaService.listar(pageable);
        return ResponseEntity.ok(cuentaDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(cuentaService.obtenerPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        cuentaService.eliminarPorId(id);
    }

    @PutMapping
    public ResponseEntity<CuentaDTO> actualizar(@RequestBody CuentaDTO cuentaDTO){
        return  new ResponseEntity<>(cuentaService.actualizar(cuentaDTO), HttpStatus.OK);
    }
}