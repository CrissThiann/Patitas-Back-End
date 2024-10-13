package pe.edu.cibertec.patitas_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend.service.AutenticacionService;

import java.io.*;
import java.time.LocalDate;

@Service
public class AutenticacionServiceImpl implements AutenticacionService{

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {

        String[] datosUsuario = null;
        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))){
            String linea;
            while ((linea = br.readLine()) != null){
                String[] datos = linea.split(";");
                if (loginRequestDTO.tipoDocumento().equals(datos[0]) &&
                   loginRequestDTO.numeroDocumento().equals(datos[1]) &&
                   loginRequestDTO.password().equals(datos[2])){

                    datosUsuario = new String[2];
                    datosUsuario[0] = datos[3]; //recupera nombre
                    datosUsuario[1] = datos[4]; //recupera Correo
                    break;

                }
            }
        } catch (Exception e) {
            datosUsuario = null;
            throw new IOException(e);
        }

        return datosUsuario;
    }




    private static final String path = "src/main/resources/logout.log";

    @Override
    public void userLogout(LogoutRequestDTO logoutRequestDTO) throws IOException {
        File file = new File(path);

        try(BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(file)))) {
            bw.write(logoutRequestDTO.tipoDocumento() + ";" + logoutRequestDTO.numeroDocumento() + ";" + LocalDate.now() + "\n");
        } catch (IOException e) {
            throw new IOException("Error al escribir en logout.txt", e);
        }
    }
}
