package com.develop.clientepersona.controller;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@RequestBody @Valid ClienteDTO clienteDTO){
        return  new ResponseEntity<>(clienteService.crear(clienteDTO), HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar(){
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(clienteService.obtenerPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        clienteService.eliminarPorId(id);
    }

    @PutMapping
    public ResponseEntity <ClienteDTO> actualizar(@RequestBody @Valid ClienteDTO clienteDTO){
        return  new ResponseEntity<>(clienteService.actualizar(clienteDTO), HttpStatus.OK);
    }

}
