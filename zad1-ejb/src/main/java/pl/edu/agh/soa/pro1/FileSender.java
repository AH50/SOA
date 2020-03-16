package pl.edu.agh.soa.pro1;

import javax.jws.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

@Stateless
@WebService
public class FileSender {
    @WebMethod
    @WebResult
    public String Send(@WebParam(name = "path") String path) {
        File file = new File(path);
        String encodedfile;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes);
        } catch (FileNotFoundException e) {
            encodedfile = "Nie ma takiego pliku";
        } catch (IOException e) {
            encodedfile = "Błąd przesyłu";
        }
        return encodedfile;
    }
}