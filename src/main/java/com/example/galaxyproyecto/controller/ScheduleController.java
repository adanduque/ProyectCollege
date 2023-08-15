package com.example.galaxyproyecto.controller;

import com.example.galaxyproyecto.model.Schedule;
import com.example.galaxyproyecto.model.Student;
import com.example.galaxyproyecto.model.Schedule;
import com.example.galaxyproyecto.model.modelassembler.ScheduleModelAssembler;
import com.example.galaxyproyecto.service.IScheduleService;
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

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping(path="/v1/schedules",
        produces= {MediaType.APPLICATION_JSON_VALUE}
        /*consumes={MediaType.APPLICATION_JSON_VALUE}*/)
public class ScheduleController {


    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private ScheduleModelAssembler scheduleModelAssembler;

    @GetMapping("/find-all")
    public CollectionModel<EntityModel<Schedule>> findAll() {

        List<Schedule> schedule= scheduleService.findAll();
        return scheduleModelAssembler.toCollectionModel(schedule);
    }

    @GetMapping(path= "/{id}",produces= {MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<Schedule> findById(@PathVariable("id") Integer id) {
        Schedule schedule =scheduleService.findById(id).orElse(null);
        return  scheduleModelAssembler.toModel(schedule);
    }


    @GetMapping("/v2/by-nombre")
    public Schedule findByLikeNombrev2(@RequestParam(name="name",defaultValue="") String nombre) {
        return null;//productoService.findById(id).orElse(null);
    }


    @PostMapping
    public EntityModel<Schedule> add(@RequestBody Schedule schedule) {
        return scheduleModelAssembler.toModel(scheduleService.add(schedule));
    }

    @PutMapping("/{id}")
    public EntityModel<Schedule> update(@PathVariable("id") Integer id, @RequestBody Schedule schedule) {
        schedule.setIdSchedule(id);
        return scheduleModelAssembler.toModel(scheduleService.update(schedule));
    }

    @DeleteMapping("/{id}")
    public EntityModel<Schedule> delete(@PathVariable("id") Integer id) {
        return scheduleModelAssembler.toModel(scheduleService.delete(id));
    }

}
