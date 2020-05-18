import models.Student;
import models.Subject;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;


import pl.edu.agh.soa.pro1.models.StudentProtobuf;
import protobuf.MessageWriter;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ClientRest {
    private ResteasyClient resteasyClient;
    private static String token = null;

    public ClientRest() {
        this.resteasyClient = new ResteasyClientBuilder().build();
    }

    public ClientRest(String username, String password) {
        this.resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget resteasyWebTarget = resteasyClient.target("http://localhost:8080/rest-api/app//authorization/login");

        Form form = new Form();
        form.param("login", username);
        form.param("password", password);
        Response response = resteasyWebTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        MultivaluedMap<String, String> headers = response.getStringHeaders();
        token = headers.getFirst("authorization");

    }


    private List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        ResteasyWebTarget resteasyWebTarget = resteasyClient.target("http://localhost:8080/rest-api/app/students");

        Response response = resteasyWebTarget.request().get();
        int responseStatus = response.getStatus();
        if (responseStatus == 200) {
            result = response.readEntity(new GenericType<List<Student>>() {
            });
        }
        response.close();
        return result;
    }

    private void addStudent(Student student) {
        ResteasyWebTarget resteasyWebTarget = resteasyClient.target("http://localhost:8080/rest-api/app/students/add");
        Response response = resteasyWebTarget.request().header("authorization", token).post(Entity.entity(student, MediaType.APPLICATION_JSON_TYPE));
        if (response.getStatus() == 201) {
            System.out.println("Student added to database\n");
        } else {
            System.out.println("Student exist in database\n");
        }
        response.close();

    }

    private Student getStudentById(int ID) {
        Student student = new Student();
        ResteasyWebTarget resteasyWebTarget = resteasyClient.target("http://localhost:8080/rest-api/app/students/" + ID);
        Response response = resteasyWebTarget.request().get();
        if (response.getStatus() == 200) {
            student = response.readEntity(Student.class);
            return student;
        }
        return student;
    }

    private void setPhoto(int ID, String photoInBase64) {

        ResteasyWebTarget resteasyWebTarget = resteasyClient.target("http://localhost:8080/rest-api/app/students/" + ID + "/photo")
                .queryParam("photo", photoInBase64);

        Response response = resteasyWebTarget.request().header("Authorization", token).put(null);
        System.out.println(response.getStatus());
        if (response.getStatus() == 200) {
            System.out.println("Photo seted\n");
        } else {
            System.out.println("Error\n");
        }
        response.close();

    }


    private void getPhoto(int ID) {
        ResteasyWebTarget resteasyWebTarget = resteasyClient.target("http://localhost:8080/rest-api/app/students/" + ID + "/photo");

        Response response = resteasyWebTarget.request().get();
        byte[] photo = response.readEntity(byte[].class);

        if (response.getStatus() == 200) {
            decodeImage(photo);
            System.out.println("Student photo downloaded");
        }

    }

    private static String codeImage(String pathToPhoto) {

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


    public static void decodeImage(byte[] Image) {
        File file = new File("rest-connector/src/main/java/photos/photoFromSerwer.jpg");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {
            byte[] decoder = Image;
            fileOutputStream.write(decoder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//PROBOBUF
private StudentProtobuf.Student testGetStudentByIdProtobuf(int id){
    StudentProtobuf.Student student = null;
    ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/rest-api/app/students/"+id+"/protobuf").register(MessageWriter.class);
    Response response = target.request().get();

    if(response.getStatus() == 200) {
        student = response.readEntity(StudentProtobuf.Student.class);
    }
    response.close();

    return student;
}


    public static void main(String[] args) {

        ClientRest client = new ClientRest("admin", "admin");

        //lista studentów
        for (Student student : client.getAllStudents()) {
            System.out.println("Student name: " + student.getName());
            System.out.println("Student surname: " + student.getSurname());
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Student photo in Base64: " + student.getPhotoInBase64());

            for (Subject subject : student.getSubjectList()) {
                System.out.println("Subject name: " + subject.getName());
                System.out.println("Subject teacher: " + subject.getTeacher());
                System.out.println("Subject ECTS: " + subject.getECTS());
            }
        }
        //dodanie studenta wraz z listą przedmiotów
        Student student = Student.builder()
                .name("Janusz")
                .surname("Kowal")
                .studentId(5555)
                .subjectList(null)
                .photoInBase64("")
                .build();

        Subject subject = Subject.builder()
                .name("Programowanie")
                .teacher("Rafał Kowalksi")
                .ECTS(5)
                .build();

        Subject subject2 = Subject.builder()
                .name("Teoria obliczeń")
                .teacher("Tomasz Nowak")
                .ECTS(3)
                .build();


        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject);
        subjects.add(subject2);

        student.setSubjectList(subjects);
        System.out.println("\nAdd new student method");
        client.addStudent(student);

        //szukanie student po id
        System.out.println("Get student by ID");
        System.out.println(client.getStudentById(5555));

        //dodanie zdjęcia studentowi
        System.out.println("\nSet photo");
        client.setPhoto(123, codeImage("rest-connector/src/main/java/photos/studentProfilePhoto.jpg"));

        //pobranie zdjęcia studenta
        System.out.println("Get photo");
        client.getPhoto(123);

        //Protobuf
        System.out.println("\nGet student by ID - Protobuf");
        System.out.println(client.testGetStudentByIdProtobuf(123));


        client.resteasyClient.close();

    }
}
