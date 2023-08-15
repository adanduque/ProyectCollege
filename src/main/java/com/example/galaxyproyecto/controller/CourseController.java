package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.Course;
import com.example.galaxyproyecto.model.Exam;
import com.example.galaxyproyecto.model.Teacher;
import com.example.galaxyproyecto.model.modelassembler.CourseModelAssembler;
import com.example.galaxyproyecto.model.modelassembler.TeacherModelAssembler;
import com.example.galaxyproyecto.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
//cambio desde gitkraken
//segundo cambio

@RequestMapping(path="/v1/courses",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)
public class CourseController {


    @Autowired
    private ICourseService courseService;

    @Autowired
    CourseModelAssembler courseModelAssembler;
  //  @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Course>> findAll() {
        List<Course> courses= courseService.findAll();
        return courseModelAssembler.toCollectionModel(courses);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public  EntityModel<Course> findById(@PathVariable("id") Integer id) {
        Course course=courseService.findById(id).orElse(null);
        return  courseModelAssembler.toModel(course);
    }

    @GetMapping("/by-nombre")
    public List<Course> findByLikeName(@RequestParam(name="name",defaultValue="") String name) {
        return courseService.findByLikeName(Course.builder().name(name).build());
    }

    @GetMapping("/v2/by-nombre")
    public Course findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }

    @GetMapping("/by-nombre/pagin")
    public Page<Course> findByLikeNamePagin(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio) {

        Pageable pageable=PageRequest.of(pagina-1, tamanio);

        return courseService.findByLikeNamePaging(pageable, name);
    }

    @GetMapping("/by-nombre/pagin-order")
    public Page<Course> findByLikeNamePaginOrden(

            @RequestParam(name="nombre",defaultValue="") String name,

            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio,

            @RequestParam(name="campo",defaultValue="id") String campo,
            @RequestParam(name="orden",defaultValue="ASC") String orden

    ) {

        Pageable pageable=PageRequest.of(pagina-1, tamanio, Sort.by(Direction.valueOf(orden), campo));

        return courseService.findByLikeNamePaging(pageable, name);
    }


    @PostMapping
    public EntityModel<Course> add(@RequestBody Course course) {
        return courseModelAssembler.toModel(courseService.add(course));
    }

    @PutMapping("/{id}")
    public EntityModel<Course> update(@PathVariable("id") Integer id, @RequestBody Course course) {
        course.setIdCourse(id);
        return courseModelAssembler.toModel(courseService.update(course));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Course> delete(@PathVariable("id") Integer id) {
        return courseModelAssembler.toModel(courseService.delete(id));
    }

}
