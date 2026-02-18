package com.cps.fct.e2e.utils.FileMapping;

import java.io.File;

public class TWIFMapper implements FileMapper {
    @Override
    public File getFile(String subtype, String fileType) {
        String path = "src/test/resources/payloads/TWIF/" + subtype.toLowerCase();
        return FileUtils.findMatchingFile(path, fileType);
    }
}


