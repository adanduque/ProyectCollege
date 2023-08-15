package com.example.galaxyproyecto.model.modelassembler;

import com.example.galaxyproyecto.controller.ScheduleController;
import com.example.galaxyproyecto.controller.TeacherController;
import com.example.galaxyproyecto.model.Schedule;
import com.example.galaxyproyecto.model.Schedule;
import com.example.galaxyproyecto.model.Teacher;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ScheduleModelAssembler implements RepresentationModelAssembler<Schedule, EntityModel<Schedule>> {

    @Override
    public EntityModel<Schedule> toModel(Schedule schedule) {
        return EntityModel.of(
                schedule,
                linkTo(methodOn(TeacherController.class).findById(schedule.getIdSchedule())).withSelfRel(),
                linkTo(methodOn(TeacherController.class).findAll()).withRel("schedules"),
                linkTo(methodOn(TeacherController.class).delete(schedule.getIdSchedule())).withRel("delete"));
    }

    @Override
    public CollectionModel<EntityModel<Schedule>> toCollectionModel(Iterable<? extends Schedule> schedules) {
        //return RepresentationModelAssembler.super.toCollectionModel(courses);
        CollectionModel<EntityModel<Schedule>> model = RepresentationModelAssembler.super.toCollectionModel(schedules);
        model.add(linkTo(methodOn(ScheduleController.class).findAll()).withSelfRel());
        model.add(Link.of(linkTo(ScheduleController.class).toUriComponentsBuilder().build().toUriString()).withRel("add"));
        return model;
    }
}
