package pe.edu.cibertec.patitas_backend.service;

import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;

public interface ServiceLogout {
    LogoutRequestDTO logout(LogoutRequestDTO logoutRequest);
}
