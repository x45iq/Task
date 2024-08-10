package io.github.x45iq.task.controllers;

import io.github.x45iq.task.models.FileEntity;
import io.github.x45iq.task.services.FilesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files/")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping("/")
    @ResponseBody
    public long post(@RequestBody FileEntity fileEntity) {
        return filesService.uploadFile(fileEntity);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<FileEntity> get(@PathVariable long id) {
        return filesService.getFile(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<FileEntity>> all() {
        var list = filesService.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }
}
