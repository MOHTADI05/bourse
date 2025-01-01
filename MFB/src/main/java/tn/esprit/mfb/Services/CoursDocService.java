package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.Repository.CoursDocReposioty;
import tn.esprit.mfb.entity.CoursDoc;

import java.io.IOException;

@Service
@AllArgsConstructor
public class CoursDocService {

    private final CoursDocReposioty coursDocReposioty ;


    public CoursDoc storeFile(MultipartFile file,Long idc) throws IOException {
        byte[] fileData = file.getBytes();

        CoursDoc metadata = new CoursDoc();

        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileType(file.getContentType());
        metadata.setFileSize(file.getSize());
        metadata.setFileData(fileData);
        metadata.setId_cours(idc);
        if (coursDocReposioty.findMaxValue() == null)
        metadata.setOrdre(1);
        else
            metadata.setOrdre(coursDocReposioty.findMaxValue()+1);

        metadata.setDescription("leçon "+metadata.getOrdre());


        return coursDocReposioty.save(metadata);
    }
}
