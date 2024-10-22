package pe.edu.cibertec.patitas_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutResponseDTO;
import pe.edu.cibertec.patitas_backend.service.AutenticacionService;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

@RestController
@RequestMapping("/autenticacion")
public class AuntenticacionController {

    @Autowired
    AutenticacionService autenticacionService;



    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {


        try {
            Thread.sleep(Duration.ofSeconds(5));
            String[] datosUsuario = autenticacionService.validarUsuario(loginRequestDTO);
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));
            if (datosUsuario == null) {
                return new LoginResponseDTO("01", "Error: Usuario no encontrado", "", "");
            }
            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1]);
        } catch (Exception e) {
            return new LoginResponseDTO("99", "Error: Ocurrio un problema", "", "");
        }


    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        try {
            Thread.sleep(Duration.ofSeconds(5));
            autenticacionService.userLogout(logoutRequestDTO);

            return ResponseEntity.ok(new LogoutResponseDTO("00", "El Cierre de Sesion fue exitoso", "", ""));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new LogoutResponseDTO("01", "Error: ocurrio un problema", "", ""));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
