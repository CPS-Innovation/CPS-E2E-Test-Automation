package com.cps.fct.e2e.utils.FileMapping;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileUtils {

    public static File findMatchingFile(String directoryPath, String fileType) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) return null;

        String normalizedType = fileType.toLowerCase().replace(" ", "_");
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().contains(normalizedType) && name.endsWith(".json"));

        return (files!=null && files.length > 0) ? files[0]:null;
    }


    public static File getValidatedFile(String caseType, String messageType, String caseDataType) {
        FileMapper mapper = FileMapperFactory.getMapper(caseType);
        File file = mapper.getFile(messageType, caseDataType);
        assertThat(file)
                .withFailMessage(" No matching file found for %s/%s with type: %s", caseType, messageType, caseDataType)
                .isNotNull();
        return file;

    }

}

