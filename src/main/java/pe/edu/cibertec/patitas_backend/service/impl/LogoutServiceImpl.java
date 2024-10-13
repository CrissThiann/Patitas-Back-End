package pe.edu.cibertec.patitas_backend.service.impl;


import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend.service.ServiceLogout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class LogoutServiceImpl implements ServiceLogout {

    private static final String LOG_FILE_PATH = "src/main/java/pe/edu/cibertec/patitas_backend/logs/logout.log";

    @Override
    public LogoutRequestDTO logout(LogoutRequestDTO logoutRequest) {

        File logFile = new File(LOG_FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, StandardCharsets.UTF_8, true))) {
            LocalDateTime now = LocalDateTime.now();
            writer.write(String.format("Tipo Documento: %s, Numero Documento: %s, Fecha: %s%n",
                    logoutRequest.tipoDocumento(), logoutRequest.numeroDocumento(), now));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
            throw new RuntimeException("Error: I/O error", e);
        }
        return logoutRequest;
    }
}
