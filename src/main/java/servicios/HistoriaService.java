package servicios;

import entidades.Restaurante;
import excepciones.RestauranteNoEncontradoException;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import modelos.RestauranteDAO;

import java.io.File;                        // import añadido
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;                  // import añadido
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;                      // import añadido

public class HistoriaService {             // antes StorageService

    private String uploadPath;

    public HistoriaService(String contextPath) {
        this.uploadPath = contextPath + "/uploads";
        new File(uploadPath).mkdirs();
    }

    public String guardarImagen(byte[] bytes, String filename) throws IOException {
        String fileExt = filename.substring(filename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + fileExt;
        Path filePath = Paths.get(uploadPath, newFilename);
        Files.write(filePath, bytes);
        return "uploads/" + newFilename;
    }
}