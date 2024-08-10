package io.github.x45iq.task.services;

import io.github.x45iq.task.models.FileEntity;
import io.github.x45iq.task.repository.FilesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilesService {
    private final FilesRepository filesRepository;

    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public Optional<FileEntity> getFile(Long id) {
        return filesRepository.findById(id);
    }

    public Long uploadFile(FileEntity fileEntity) {
        return filesRepository.save(fileEntity).getId();
    }

    public List<FileEntity> getAll() {
        return filesRepository.findAllByOrderByCreationDateAsc();
    }
}
