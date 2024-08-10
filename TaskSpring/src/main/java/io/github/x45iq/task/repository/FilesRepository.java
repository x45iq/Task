package io.github.x45iq.task.repository;

import io.github.x45iq.task.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;
import java.util.List;

public interface FilesRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByOrderByCreationDateAsc();
}