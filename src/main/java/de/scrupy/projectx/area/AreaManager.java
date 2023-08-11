package de.scrupy.projectx.area;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.scrupy.projectx.ProjectX;

import javax.annotation.Nullable;
import java.io.*;

public class AreaManager {
    private static final String AREA_FOLDER_PATH = ProjectX.getInstance().getDataFolder() + "/area";
    private static final String AREA_FILE = "area.json";
    private final Area area;
    private final Gson gson;

    public AreaManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Area.class, new AreaSerializer())
                .create();
        createFolderIfNotExists();
        area = loadArea();
    }

    private void createFolderIfNotExists() {
        File areaFolder = getAreaFolder();
        if (!areaFolder.exists()) {
            if (!areaFolder.mkdirs()) {
                ProjectX.getInstance().getLogger().warning("Error while creating area folder.");
            }
        }
    }

    public void saveArea(Area area) throws IOException {
        FileWriter fileWriter = new FileWriter(getAreaFile());
        gson.toJson(area, fileWriter);
        fileWriter.close();
    }

    @Nullable
    private Area loadArea() {
        File area = getAreaFile();
        if (area.exists()) {
            try {
                return gson.fromJson(new FileReader(area), Area.class);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private File getAreaFile() {
       return new File(AREA_FOLDER_PATH + "/" + AREA_FILE);
    }

    private File getAreaFolder() {
        return new File(AREA_FOLDER_PATH);
    }

    @Nullable
    public Area getArea() {
        return area;
    }
}
