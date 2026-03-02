package com.cps.fct.e2e.utils.FileMapping;

//import java.io.File;
//
//public class DCFMapper implements FileMapper {
//    @Override
//    public File getFile(String subtype, String fileType) {
//        String path = "src/test/resources/payloads/DCF/" + subtype.toLowerCase();
//        return FileUtils.findMatchingFile(path, fileType);
//    }
//}

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class DCFMapper implements FileMapper {

    private static final String BASE_DIR = "src/test/resources/payloads/DCF";

    @Override
    public File getFile(String subtype, String fileType) {
        String lowerSubtype  = subtype.toLowerCase(Locale.ROOT).trim();
        String lowerFileType = fileType.toLowerCase(Locale.ROOT).trim();

        String dir = BASE_DIR + "/" + lowerSubtype; // e.g. .../payloads/DCF/lm04

        if ("lm04".equals(lowerSubtype)) {
            String normalized = lowerFileType.replace(' ', '_'); // "witness_child", "victim_police", etc.

            // 1️⃣ Override metadata payloads first
            if ("dcf_witness_meta_data".equals(normalized)) {
                return Paths.get(dir, "dcf_witness_meta_data.json").toFile();
            }
            if ("dcf_victim_meta_data".equals(normalized)) {
                return Paths.get(dir, "dcf_victim_meta_data.json").toFile();
            }

            // 2️⃣ All witness* variants → witness.json, witness_child.json, witness_police.json, etc.
            Path path = Paths.get(dir, normalized + ".json");
            if (normalized.startsWith("witness")) {
                return path.toFile();
            }

            // 3️⃣ All victim* variants → victim.json, victim_child.json, victim_police.json, etc.
            if (normalized.startsWith("victim")) {
                return path.toFile();
            }

            // 4️⃣ Anything else → old fuzzy logic
            return FileUtils.findMatchingFile(dir, fileType);
        }

        // Other DCF message types unchanged
        return FileUtils.findMatchingFile(dir, fileType);
    }
}