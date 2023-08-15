package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.Note;
import com.example.galaxyproyecto.model.Note;
import com.example.galaxyproyecto.model.modelassembler.NoteModelAssembler;
import com.example.galaxyproyecto.service.INoteService;

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
@RequestMapping(path="/v1/notes",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)

public class NoteController {
    @Autowired
    private INoteService noteService;

    @Autowired
    NoteModelAssembler noteModelAssembler;
    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Note>> findAll() {
        List<Note> notes= noteService.findAll();
        return noteModelAssembler.toCollectionModel(notes);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public  EntityModel<Note> findById(@PathVariable("id") Integer id) {
        Note note=noteService.findById(id).orElse(null);
        return  noteModelAssembler.toModel(note);
    }


    @GetMapping("/v2/by-nombre")
    public Note findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }


    @PostMapping
    public EntityModel<Note> add(@RequestBody Note note) {
        return noteModelAssembler.toModel(noteService.add(note));
    }

    @PutMapping("/{id}")
    public EntityModel<Note> update(@PathVariable("id") Integer id, @RequestBody Note note) {
        note.setIdNote(id);
        return noteModelAssembler.toModel(noteService.update(note));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Note> delete(@PathVariable("id") Integer id) {
        return noteModelAssembler.toModel(noteService.delete(id));
    }

}
