package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.Programming;
import com.example.galaxyproyecto.model.Programming;
import com.example.galaxyproyecto.model.Programming;
import com.example.galaxyproyecto.model.modelassembler.ProgrammingModelAssembler;
import com.example.galaxyproyecto.service.IProgrammingService;
import com.example.galaxyproyecto.service.IProgrammingService;
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
@RequestMapping(path="/v1/programmings",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)
public class ProgrammingController {


    @Autowired
    private IProgrammingService programmingService;

    @Autowired
    ProgrammingModelAssembler programmingModelAssembler;
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Programming>> findAll() {
        List<Programming> programmings= programmingService.findAll();
        return programmingModelAssembler.toCollectionModel(programmings);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public  EntityModel<Programming> findById(@PathVariable("id") Integer id) {
        Programming programming=programmingService.findById(id).orElse(null);
        return  programmingModelAssembler.toModel(programming);
    }


    @GetMapping("/v2/by-nombre")
    public Programming findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }


    @PostMapping
    public EntityModel<Programming> add(@RequestBody Programming programming) {
        return programmingModelAssembler.toModel(programmingService.add(programming));
    }

    @PutMapping("/{id}")
    public EntityModel<Programming> update(@PathVariable("id") Integer id, @RequestBody Programming programming) {
        programming.setIdProgramming(id);
        return programmingModelAssembler.toModel(programmingService.update(programming));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Programming> delete(@PathVariable("id") Integer id) {
        return programmingModelAssembler.toModel(programmingService.delete(id));
    }
}
