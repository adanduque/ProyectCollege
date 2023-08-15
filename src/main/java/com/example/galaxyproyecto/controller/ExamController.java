package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.Exam;
import com.example.galaxyproyecto.model.Exam;
import com.example.galaxyproyecto.model.modelassembler.ExamModelAssembler;
import com.example.galaxyproyecto.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/v1/exams",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)

public class ExamController {
    @Autowired
    private IExamService examService;

    @Autowired
    ExamModelAssembler examModelAssembler;
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Exam>> findAll() {
        List<Exam> exams= examService.findAll();
        return examModelAssembler.toCollectionModel(exams);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public  EntityModel<Exam> findById(@PathVariable("id") Integer id) {
        Exam exam=examService.findById(id).orElse(null);
        return  examModelAssembler.toModel(exam);
    }

    @GetMapping("/by-nombre")
    public List<Exam> findByLikeName(@RequestParam(name="name",defaultValue="") String name) {
        return examService.findByLikeName(Exam.builder().name(name).build());
    }

    @GetMapping("/v2/by-nombre")
    public Exam findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }

    @GetMapping("/by-nombre/pagin")
    public Page<Exam> findByLikeNamePagin(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio) {

        Pageable pageable= PageRequest.of(pagina-1, tamanio);

        return examService.findByLikeNamePaging(pageable, name);
    }

    @GetMapping("/by-nombre/pagin-order")
    public Page<Exam> findByLikeNamePaginOrden(

            @RequestParam(name="nombre",defaultValue="") String name,

            @RequestParam(name="pagina",defaultValue="1") Integer pagina,
            @RequestParam(name="tamanio",defaultValue="10") Integer tamanio,

            @RequestParam(name="campo",defaultValue="id") String campo,
            @RequestParam(name="orden",defaultValue="ASC") String orden

    ) {

        Pageable pageable=PageRequest.of(pagina-1, tamanio, Sort.by(Sort.Direction.valueOf(orden), campo));

        return examService.findByLikeNamePaging(pageable, name);
    }


    @PostMapping
    public EntityModel<Exam> add(@RequestBody Exam exam) {
        return examModelAssembler.toModel(examService.add(exam));
    }

    @PutMapping("/{id}")
    public EntityModel<Exam> update(@PathVariable("id") Integer id, @RequestBody Exam exam) {
        exam.setIdExam(id);
        return examModelAssembler.toModel(examService.update(exam));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Exam> delete(@PathVariable("id") Integer id) {
        return examModelAssembler.toModel(examService.delete(id));
    }

}
