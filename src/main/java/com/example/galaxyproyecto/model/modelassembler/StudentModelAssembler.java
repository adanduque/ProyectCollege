package com.example.galaxyproyecto.model.modelassembler;

import com.example.galaxyproyecto.controller.CourseController;
import com.example.galaxyproyecto.controller.StudentController;
import com.example.galaxyproyecto.model.Course;
import com.example.galaxyproyecto.model.Student;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentModelAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {

    @Override
    public EntityModel<Student> toModel(Student student) {
        return EntityModel.of(
                student,
                linkTo(methodOn(StudentController.class).findById(student.getIdStudent())).withSelfRel(),
                linkTo(methodOn(StudentController.class).findAll()).withRel("students"),
                linkTo(methodOn(StudentController.class).delete(student.getIdStudent())).withRel("delete"));
    }

    @Override
    public CollectionModel<EntityModel<Student>> toCollectionModel(Iterable<? extends Student> students) {
        //return RepresentationModelAssembler.super.toCollectionModel(courses);
        CollectionModel<EntityModel<Student>> model = RepresentationModelAssembler.super.toCollectionModel(students);
        model.add(linkTo(methodOn(StudentController.class).findAll()).withSelfRel());
        model.add(Link.of(linkTo(StudentController.class).toUriComponentsBuilder().build().toUriString()).withRel("add"));
        return model;
    }
}
