package pl.edu.agh.soa.pro1.api;

import io.swagger.annotations.*;
import pl.edu.agh.soa.pro1.JWT.JWTTokenNeeded;
import pl.edu.agh.soa.pro1.models.Student;
import pl.edu.agh.soa.pro1.models.StudentRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.Base64;
import java.util.List;

@Api(value = "StudentApi")
@Path("/students")
public class StudentController {

    static StudentRepository studentRepository = new StudentRepository();

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello world!";
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents(@QueryParam("ID") String Id,
                                   @QueryParam("firstName") String name,
                                   @QueryParam("surname") String surname) {
        List<Student> studentsList = studentRepository.getStudentList();

        if (studentsList == null || studentsList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("Student database is empty").build();

        return Response.status(Response.Status.OK).entity(studentsList).build();
    }

    @GET
    @Path("/{studentID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("studentID") int studentID) {
        Student student = studentRepository.getStudentByID(studentID);

        if(student!=null){
            return Response.status(Response.Status.OK).entity(student).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @PUT
    @Path("/{studentID}/photo")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response setPhoto(@PathParam("studentID") int studentID , @ApiParam(required=true) String photoBase64) {

        List<Student> studentsList = studentRepository.getStudentList();

        for (Student student : studentsList) {
            if (student.getStudentId() == studentID) {
                student.setPhotoInBase64(photoBase64);
                return Response.status(Response.Status.OK).entity(student).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @PUT
    @Path("/{studentID}/surname")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response updateStudentSurname(@PathParam("studentID") int studentID, @ApiParam(required=true, name = "Surname") String newSurname) {

        Student student = studentRepository.changeStudentSurname(studentID,newSurname);
        if(student!=null){
            return Response.status(Response.Status.OK).entity(student).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    @GET
    @Path("/{studentID}/photo")
    @Produces("image/jpeg")
    @ApiOperation(value = "Get student photo", response = byte.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "This is student photo."),
            @ApiResponse(code = 404, message = "Student doesn't exist.")})

    public Response getStudentPhoto(@PathParam("studentID") int studentID) {

        Student student = studentRepository.getStudentByID(studentID);

        byte[] imageBytes = Base64.getDecoder().decode(student.getPhotoInBase64());
        if(student!=null){
            return Response.status(Response.Status.OK).entity(imageBytes).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add student", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student added.", response = Student.class),
            @ApiResponse(code = 409, message = "Student already exists.")})
    @JWTTokenNeeded
    public Response addStudent(Student student) {

        Response r = getStudentById(student.getStudentId());
        if (r.getStatus() == 404) {
            studentRepository.addStudent(student);
            return Response.status(Response.Status.CREATED).entity(student).build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Student alredy exists").build();
    }
}
