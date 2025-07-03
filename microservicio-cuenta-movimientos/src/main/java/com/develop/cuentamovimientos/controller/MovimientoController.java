package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.MovimientoClienteReporteDTO;
import com.develop.cuentamovimientos.dto.MovimientoDTO;
import com.develop.cuentamovimientos.service.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public ResponseEntity<List<MovimientoDTO>> listar(){
        return new ResponseEntity<>(movimientoService.listar(), HttpStatus.OK);
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
    public ResponseEntity <MovimientoDTO> actualizar(@RequestBody MovimientoDTO movimientoDTO){
        return  new ResponseEntity<>(movimientoService.crear(movimientoDTO), HttpStatus.OK);
    }

    @GetMapping("/movimientos-por-fecha")
    public ResponseEntity<List<MovimientoClienteReporteDTO>> obtenerMovimientosClientePorFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String identificacion,
            @RequestParam(required = false) String nombre
    ) {
        List<MovimientoClienteReporteDTO> movimientos =
                movimientoService.obtenerMovimientosPorFechasYCliente(fechaInicio, fechaFin, identificacion, nombre);
        return ResponseEntity.ok(movimientos);
    }

}