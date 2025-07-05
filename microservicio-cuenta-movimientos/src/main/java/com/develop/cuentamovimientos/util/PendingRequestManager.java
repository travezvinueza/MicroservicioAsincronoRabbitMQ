package com.develop.cuentamovimientos.util;

import com.develop.cuentamovimientos.dto.ClienteDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PendingRequestManager {
    private final Map<String, CompletableFuture<ClienteDTO>> pendingRequests = new ConcurrentHashMap<>();

    public void put(String identificacion, CompletableFuture<ClienteDTO> future) {
        pendingRequests.put(identificacion, future);
    }

    public CompletableFuture<ClienteDTO> get(String identificacion) {
        return pendingRequests.get(identificacion);
    }

    public void complete(String identificacion, ClienteDTO cliente) {
        CompletableFuture<ClienteDTO> future = pendingRequests.remove(identificacion);
        if (future != null) {
            future.complete(cliente);
        }
    }

    public void fail(String identificacion, Throwable ex) {
        CompletableFuture<ClienteDTO> future = pendingRequests.remove(identificacion);
        if (future != null) {
            future.completeExceptionally(ex);
        }
    }
}

