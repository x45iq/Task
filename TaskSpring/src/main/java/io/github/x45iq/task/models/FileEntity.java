package io.github.x45iq.task.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.x45iq.task.converters.DataConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"data"})
@Builder
@Data
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data", columnDefinition = "bytea", nullable = false)
    @Convert(converter = DataConverter.class)
    private String data;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "creation_date",nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
}
