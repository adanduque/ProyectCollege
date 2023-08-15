package com.example.galaxyproyecto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COURSE_INSCRIPTIONS")
public class CourseInscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCourseInscription")
    @SequenceGenerator(sequenceName = "SEQ_COURSE_INSCRIPTION", allocationSize = 1, name = "seqCourseInscription")
    private Integer idCourseInscription;

    @ManyToOne
    @JoinColumn(name = "id_programming", nullable = false, foreignKey = @ForeignKey(name = "FK_COURSE_INSCRIPTIONS_PROGRAMMING"))
    private Programming programming;

    @ManyToMany
    @JoinColumn(name = "id_student", nullable = false, foreignKey = @ForeignKey(name = "FK_COURSE_INSCRIPTIONS_STUDENT"))
    private List<Student> studentsList;

    @Column(nullable = false)
    private LocalDateTime inscriptionDate;

    @Column(nullable = false,length = 150)
    private String description;

    @Column(nullable = false)
    private Integer state;
}
