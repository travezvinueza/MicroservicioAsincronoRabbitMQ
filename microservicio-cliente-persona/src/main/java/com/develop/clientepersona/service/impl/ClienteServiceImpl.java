package com.develop.clientepersona.service.impl;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.entity.Cliente;
import com.develop.clientepersona.entity.MensajeError;
import com.develop.clientepersona.exception.CedulaInvalidaException;
import com.develop.clientepersona.exception.RecursoNoEncontradoException;
import com.develop.clientepersona.repository.ClienteRepository;
import com.develop.clientepersona.service.ClienteService;
import com.develop.clientepersona.util.ValidarIdentificacionCedula;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ValidarIdentificacionCedula validarIdentificacionCedula;

    @Override
    public ClienteDTO crear(ClienteDTO clienteDTO) {
        if(!validarIdentificacionCedula.validarCedula(clienteDTO.getIdentification())){
            throw new CedulaInvalidaException(MensajeError.IDENTIFICACION_NO_VALIDO);
        }

        Cliente cliente = modelMapper.map(clienteDTO,Cliente.class);
        cliente.setPassword(bCryptPasswordEncoder.encode(clienteDTO.getPassword()));
        return modelMapper.map(clienteRepository.save(cliente),ClienteDTO.class);
    }

    @Override
    public Page<ClienteDTO> listar(Pageable pageable) {
        Page<Cliente> clientePage = clienteRepository.findAll(pageable);
        return clientePage.map(cliente -> modelMapper.map(cliente, ClienteDTO.class));
    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        Cliente cliente=clienteRepository.findById(id).orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
        return modelMapper.map(cliente,ClienteDTO.class);
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDTO) {

        Cliente clienteDB= modelMapper.map(obtenerPorId(clienteDTO.getId()),Cliente.class);

        clienteDB.setFullName(clienteDTO.getFullName());
        clienteDB.setGenderPerson(clienteDTO.getGenderPerson());
        clienteDB.setAge(clienteDTO.getAge());
        clienteDB.setAddress(clienteDTO.getAddress());
        clienteDB.setPhone(clienteDTO.getPhone());
        clienteDB.setState(clienteDTO.isState());
        return modelMapper.map(clienteRepository.save(clienteDB), ClienteDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        clienteRepository.findById(id).orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
        clienteRepository.deleteById(id);
    }
}
