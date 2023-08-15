package com.example.galaxyproyecto.controller;
import java.util.List;

import com.example.galaxyproyecto.model.Student;
import com.example.galaxyproyecto.model.Teacher;
import com.example.galaxyproyecto.model.modelassembler.TeacherModelAssembler;
import com.example.galaxyproyecto.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/v1/teachers",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)
public class TeacherController {


    @Autowired
    private ITeacherService teacherService;
    @Autowired
    TeacherModelAssembler teacherModelAssembler;
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Teacher>> findAll() {
     //   return teacherService.findAll();
        List<Teacher> teachers= teacherService.findAll();
        return teacherModelAssembler.toCollectionModel(teachers);
    }




    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<Teacher> findById(@PathVariable("id") Integer id) {

        Teacher teacher=teacherService.findById(id).orElse(null);
        return  teacherModelAssembler.toModel(teacher);


    }
    @GetMapping("/by-nombre")
    public List<Teacher> findByLikeName(@RequestParam(name="name",defaultValue="") String name) {
        return teacherService.findByLikeName(Teacher.builder().name(name).build());
    }

    @GetMapping("/v2/by-nombre")
    public Teacher findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }

    @GetMapping("/by-nombre/pagin")
    public Page<Teacher> findByLikeNombrePagin(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio) {

        Pageable pageable=PageRequest.of(pagina-1, tamanio);

        return teacherService.findByLikeNamePaging(pageable, name);
    }

    @GetMapping("/by-nombre/pagin-order")
    public Page<Teacher> findByLikeNombrePaginOrden(

            @RequestParam(name="nombre",defaultValue="") String name,

            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio,

            @RequestParam(name="campo",defaultValue="id") String campo,
            @RequestParam(name="orden",defaultValue="ASC") String orden

    ) {

        Pageable pageable=PageRequest.of(pagina-1, tamanio, Sort.by(Direction.valueOf(orden), campo));

        return teacherService.findByLikeNamePaging(pageable, name);
    }


    @PostMapping
    public EntityModel<Teacher> add(@RequestBody Teacher teacher) {
        return teacherModelAssembler.toModel(teacherService.add(teacher));
    }

    @PutMapping("/{id}")
    public EntityModel<Teacher> update(@PathVariable("id") Integer id, @RequestBody Teacher teacher) {
        teacher.setIdTeacher(id);
        return teacherModelAssembler.toModel(teacherService.update(teacher));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Teacher> delete(@PathVariable("id") Integer id) {
        return teacherModelAssembler.toModel(teacherService.delete(id));
    }
}
