package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.dto.ClienteDTO;
import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.entity.Cuenta;
import com.develop.cuentamovimientos.entity.MensajeError;
import com.develop.cuentamovimientos.exception.CuentaNoEncontradaException;
import com.develop.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.service.ClienteRequestProducerService;
import com.develop.cuentamovimientos.service.ClienteResponseConsumer;
import com.develop.cuentamovimientos.service.CuentaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ModelMapper modelMapper;
    private final ClienteRequestProducerService clienteRequestProducerService;
    private final ClienteResponseConsumer clienteResponseConsumer;


    @Override
    public CuentaDTO crear(CuentaDTO cuentaDTO) {
        if (cuentaRepository.findByAccountNumber(cuentaDTO.getAccountNumber()).isPresent()) {
            throw new CuentaNoEncontradaException("❌ El número de cuenta ya existe");
        }

        String identification = cuentaDTO.getIdentificationClient();
        clienteRequestProducerService.obtenerClientePorIdentificacion(identification);

        ClienteDTO clienteDTO;
        try {
            clienteDTO = clienteResponseConsumer.obtenerClienteDTO().get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RecursoNoEncontradoException("❌ Error al obtener el cliente: " + e.getMessage());
        }

        cuentaDTO.setIdentificationClient(clienteDTO.getIdentification());

        Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
        Cuenta saved = cuentaRepository.save(cuenta);

        return modelMapper.map(saved, CuentaDTO.class);
    }

    @Override
    public Page<CuentaDTO> listar(Pageable pageable) {
        Page<Cuenta> cuentaPage = cuentaRepository.findAll(pageable);
        return cuentaPage.map(cuenta -> modelMapper.map(cuenta, CuentaDTO.class));
    }

    @Override
    public CuentaDTO obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
        return modelMapper.map(cuenta,CuentaDTO.class);
    }

    @Override
    public CuentaDTO actualizar(CuentaDTO cuentaDTO) {
        CuentaDTO cuentaDTODB= obtenerPorId(cuentaDTO.getId());

        cuentaDTODB.setAccountNumber(cuentaDTO.getAccountNumber());
        cuentaDTODB.setAccountType(cuentaDTO.getAccountType());
        cuentaDTODB.setInitialBalance(cuentaDTO.getInitialBalance());
        cuentaDTODB.setState(cuentaDTO.isState());
        cuentaDTODB.setIdentificationClient(cuentaDTO.getIdentificationClient());

        Cuenta cuenta = modelMapper.map(cuentaDTODB, Cuenta.class);

        return modelMapper.map(cuentaRepository.save(cuenta), CuentaDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        cuentaRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
        cuentaRepository.deleteById(id);
    }

    @Override
    public List<CuentaDTO> findByIdentificacionCliente(String identificationClient) {
        return cuentaRepository.findByIdentificationClient(identificationClient).stream().map(
                cuenta-> modelMapper.map(cuenta,CuentaDTO.class)).toList();
    }
}
