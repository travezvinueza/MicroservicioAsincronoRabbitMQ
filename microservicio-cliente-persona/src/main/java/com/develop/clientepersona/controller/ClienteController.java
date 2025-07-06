package com.develop.clientepersona.controller;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ClienteDTO>> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDTO> clienteDTOPage = clienteService.listar(pageable);
        return ResponseEntity.ok(clienteDTOPage);
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
    public ResponseEntity<ClienteDTO> actualizar(@RequestBody @Valid ClienteDTO clienteDTO){
        return new ResponseEntity<>(clienteService.actualizar(clienteDTO), HttpStatus.OK);
    }

}
