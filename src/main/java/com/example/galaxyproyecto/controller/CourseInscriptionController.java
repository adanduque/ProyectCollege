package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.CourseInscription;
import com.example.galaxyproyecto.model.CourseInscription;
import com.example.galaxyproyecto.model.modelassembler.CourseInscriptionModelAssembler;
import com.example.galaxyproyecto.service.ICourseInscriptionService;
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
@RequestMapping(path="/v1/courseInscriptions",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)
public class CourseInscriptionController {

    @Autowired
    private ICourseInscriptionService courseInscriptionService;

    @Autowired
    CourseInscriptionModelAssembler courseInscriptionModelAssembler;
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<CourseInscription>> findAll() {
        List<CourseInscription> courseInscriptions= courseInscriptionService.findAll();
        return courseInscriptionModelAssembler.toCollectionModel(courseInscriptions);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public  EntityModel<CourseInscription> findById(@PathVariable("id") Integer id) {
        CourseInscription courseInscription=courseInscriptionService.findById(id).orElse(null);
        return  courseInscriptionModelAssembler.toModel(courseInscription);
    }



    @GetMapping("/v2/by-nombre")
    public CourseInscription findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }

    @PostMapping
    public EntityModel<CourseInscription> add(@RequestBody CourseInscription courseInscription) {
        return courseInscriptionModelAssembler.toModel(courseInscriptionService.add(courseInscription));
    }

    @PutMapping("/{id}")
    public EntityModel<CourseInscription> update(@PathVariable("id") Integer id, @RequestBody CourseInscription courseInscription) {
        courseInscription.setIdCourseInscription(id);
        return courseInscriptionModelAssembler.toModel(courseInscriptionService.update(courseInscription));
    }

    @DeleteMapping("/{id}")
    public EntityModel<CourseInscription> delete(@PathVariable("id") Integer id) {
        return courseInscriptionModelAssembler.toModel(courseInscriptionService.delete(id));
    }


}
