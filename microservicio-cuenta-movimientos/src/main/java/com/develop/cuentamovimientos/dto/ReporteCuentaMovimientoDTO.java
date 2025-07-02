package com.develop.cuentamovimientos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCuentaMovimientoDTO {
    private CuentaDTO cuentaDTO;
    private List<MovimientoDTO> movimientoDTO;
}
