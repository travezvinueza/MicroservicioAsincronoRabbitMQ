package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.entity.Cuenta;
import com.develop.cuentamovimientos.entity.MensajeError;
import com.develop.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.service.CuentaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CuentaServiceImpl implements CuentaService {
    private CuentaRepository cuentaRepository;
    private ModelMapper modelMapper;

    @Override
    public CuentaDTO crear(CuentaDTO cuentaDTO) {
        Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
        return modelMapper.map(cuentaRepository.save(cuenta),CuentaDTO.class);
    }

    @Override
    public List<CuentaDTO> listar() {
        return cuentaRepository.findAll().stream().map(
                cuenta-> modelMapper.map(cuenta,CuentaDTO.class)).toList();
    }

    @Override
    public CuentaDTO obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
        return modelMapper.map(cuenta,CuentaDTO.class);
    }

    @Override
    public CuentaDTO actualizar(CuentaDTO cuentaDTO) {
        CuentaDTO cuentaDTODB= obtenerPorId(cuentaDTO.getId());

        cuentaDTODB.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaDTODB.setAccountType(cuentaDTO.getAccountType());
        cuentaDTODB.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuentaDTODB.setEstado(cuentaDTO.isEstado());

        Cuenta cuenta = modelMapper.map(cuentaDTODB, Cuenta.class);

        return modelMapper.map(cuentaRepository.save(cuenta), CuentaDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        cuentaRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
        cuentaRepository.deleteById(id);
    }

    @Override
    public List<CuentaDTO> findByIdentificacionCliente(String identificacionCliente) {
        return cuentaRepository.findByIdentificacionCliente(identificacionCliente).stream().map(
                cuenta-> modelMapper.map(cuenta,CuentaDTO.class)).toList();
    }
}
