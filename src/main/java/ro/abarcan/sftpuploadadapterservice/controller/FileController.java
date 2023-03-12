package ro.abarcan.sftpuploadadapterservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.abarcan.sftpuploadadapterservice.service.FileService;

@RestController
@RequestMapping("/v1/upload")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public void uploadFile(@RequestParam String path,
                           @RequestPart("file") MultipartFile file) {
        fileService.uploadFile(file, path);
    }
}
