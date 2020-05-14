package pl.edu.agh.soa.pro1.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.edu.agh.soa.pro1.models.Student;
import pl.edu.agh.soa.pro1.models.StudentRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Api(value = "StudentApi")
@Path("/student")
public class StudentController {

    static StudentRepository studentRepository = new StudentRepository();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello world!";
    }

    @GET
    @Path("/getAllstudents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents(@QueryParam("firstName") String firstName) {
        List<Student> studentsList = studentRepository.getStudentList();

        if (studentsList == null || studentsList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("Student database is empty").build();

        return Response.status(Response.Status.OK).entity(studentsList).build();
    }

    @GET
    @Path("/getStudent/{studentID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("studentID") int studentID) {

        List<Student> studentsList = studentRepository.getStudentList();

        for (Student student : studentsList) {
            if (student.getStudentId() == studentID) {
                return Response.status(Response.Status.OK).entity(student).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @GET
    @Path("/setPhoto/{studentID}/{photoBase64}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setPhoto(@PathParam("studentID") int studentID, @PathParam("photoBase64") String photoBase64) {

        List<Student> studentsList = studentRepository.getStudentList();

        for (Student student : studentsList) {
            if (student.getStudentId() == studentID) {
                student.setPhotoInBase64(photoBase64);
                return Response.status(Response.Status.OK).entity(student).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @GET
    @Path("/updateStudentSurname/{studentID}/{newSurname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudentSurname(@PathParam("studentID") int studentID, @PathParam("newSurname") String newSurname) {

        List<Student> studentsList = studentRepository.getStudentList();
        for (Student student : studentsList) {
            if (student.getStudentId() == studentID) {
                student.setSurname(newSurname);
                return Response.status(Response.Status.OK).entity(student).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @GET
    @Path("/getPhoto/{studentID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add student", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "This is student photo.",response = String.class),
            @ApiResponse(code = 404, message = "Student doesn't exist.")})
    public Response getStudentPhoto(@PathParam("studentID") int studentID) {

        List<Student> studentsList = studentRepository.getStudentList();
        for (Student student : studentsList) {
            if (student.getStudentId() == studentID) {
                return Response.status(Response.Status.OK).entity(student.getPhotoInBase64()).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add student", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student added.",response = Student.class),
            @ApiResponse(code = 409, message = "Student already exists.")})
    public Response addStudent(Student student) {

        Response r = getStudentById(student.getStudentId());
        if (r.getStatus() == 404) {
            studentRepository.addStudent(student);
            return Response.status(Response.Status.CREATED).entity(student).build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Student alredy exists").build();

    }
}
