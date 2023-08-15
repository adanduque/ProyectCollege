package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.Course;
import com.example.galaxyproyecto.model.Schedule;
import com.example.galaxyproyecto.model.Student;
import com.example.galaxyproyecto.model.Teacher;
import com.example.galaxyproyecto.model.modelassembler.StudentModelAssembler;
import com.example.galaxyproyecto.model.modelassembler.TeacherModelAssembler;
import com.example.galaxyproyecto.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path="/v1/students",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)
public class StudentController {


    @Autowired
    private IStudentService studentService;
   // @CrossOrigin(origins = "http://localhost:8080")

    @Autowired
    StudentModelAssembler studentModelAssembler;
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Student>>  findAll() {
        List<Student> students= studentService.findAll();
        return studentModelAssembler.toCollectionModel(students);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<Student> findById(@PathVariable("id") Integer id) {
        Student student =studentService.findById(id).orElse(null);
        return  studentModelAssembler.toModel(student);

    }

    @GetMapping("/by-nombre")
    public List<Student> findByLikeName(@RequestParam(name="name",defaultValue="") String name) {
        return studentService.findByLikeName(Student.builder().name(name).build());
    }

    @GetMapping("/v2/by-nombre")
    public Student findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//
    }

    @GetMapping("/by-nombre/pagin")
    public Page<Student> findByLikeNombrePagin(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio) {

        Pageable pageable= PageRequest.of(pagina-1, tamanio);

        return studentService.findByLikeNamePaging(pageable, name);
    }

    @GetMapping("/by-nombre/pagin-order")
    public Page<Student> findByLikeNombrePaginOrden(

            @RequestParam(name="nombre",defaultValue="") String name,

            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio,

            @RequestParam(name="campo",defaultValue="id") String campo,
            @RequestParam(name="orden",defaultValue="ASC") String orden

    ) {

        Pageable pageable=PageRequest.of(pagina-1, tamanio, Sort.by(Sort.Direction.valueOf(orden), campo));

        return studentService.findByLikeNamePaging(pageable, name);
    }


    @PostMapping
    public EntityModel<Student> add(@RequestBody Student student) {
        return studentModelAssembler.toModel(studentService.add(student));
    }

    @PutMapping("/{id}")
    public EntityModel<Student> update(@PathVariable("id") Integer id, @RequestBody Student student) {
        student.setIdStudent(id);
        return studentModelAssembler.toModel(studentService.update(student));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Student> delete(@PathVariable("id") Integer id) {
        return studentModelAssembler.toModel(studentService.delete(id));
    }

}
