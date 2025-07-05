package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.service.CuentaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CuentaDTO>> listar(){
        return new ResponseEntity<>(cuentaService.listar(), HttpStatus.OK);
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