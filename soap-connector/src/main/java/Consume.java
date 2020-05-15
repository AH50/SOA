import pl.edu.agh.soa.pro1.*;

import javax.xml.ws.BindingProvider;
import java.io.*;
import java.util.Base64;
import java.util.Map;


public class Consume {
    public static void main(String[] args) throws IOException {
        StudentControllerService studentservice = new StudentControllerService();
        StudentController studentController = studentservice.getStudentControllerPort();

        Map<String, Object> reqContext = ((BindingProvider)
                studentController).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "Jacek1");
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "placek");

        //dodanie 2 studentów
        System.out.println(studentController.mockData());
        for (Student student : studentController.allStudents())
        {
            System.out.println("Name: "+student.getName());
            System.out.println("Surname: "+student.getSurname());
            System.out.println("Student ID: "+student.getStudentId());

            for(Subject subject : student.getSubjects().getSubjectList()){
                System.out.println("Subject name: "+subject.getName());
                System.out.println("Subject teacher: "+subject.getTeacher());
                System.out.println("Subject ECTS: "+subject.getECTS());
            }
        }

        //aktualizacja nazwiska
        studentController.updateStudentSurname(123,"Kowal");

        //ustawienie zdjęcia dla studenta z id nr 123 i pobranie tego zdjęcia z serwera i zapisanie w folderze photos pod nazwą "photoFromSerwer.jpg"
        if (studentController.findStudent(123) != null) {
            studentController.setPhoto(123, codeImage("soap-connector/src/main/java/photos/studentProfilePhoto.jpg"));
            decodeImage(studentController.getStudentPhoto(123));
        }
    }

    public static String codeImage(String pathToPhoto) {

        File file = new File(pathToPhoto);
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

    public static void decodeImage(String ImageBase64) {
        File file = new File("soap-connector/src/main/java/photos/photoFromSerwer.jpg");
        try(FileOutputStream fileOutputStream = new FileOutputStream(file);) {
            byte[] decoder = Base64.getDecoder().decode(ImageBase64);
            fileOutputStream.write(decoder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}