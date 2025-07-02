package com.develop.cuentamovimientos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaDTO {
    private ClienteDTO clienteDTO;
    private List<ReporteCuentaMovimientoDTO> reporteCuentaMovimientos;
}
