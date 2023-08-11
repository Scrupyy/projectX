package de.scrupy.projectx.area;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class AreaSerializer implements JsonSerializer<Area>, JsonDeserializer<Area> {
    @Override
    public JsonElement serialize(Area src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        JsonObject xObject = new JsonObject();
        xObject.addProperty("world", src.getCornerOne().getWorld().getName());
        xObject.addProperty("x", src.getCornerOne().getX());
        xObject.addProperty("y", src.getCornerOne().getY());
        xObject.addProperty("z", src.getCornerOne().getZ());
        jsonObject.add("cornerOne", xObject);

        JsonObject yObject = new JsonObject();
        yObject.addProperty("world", src.getCornerTwo().getWorld().getName());
        yObject.addProperty("x", src.getCornerTwo().getX());
        yObject.addProperty("y", src.getCornerTwo().getY());
        yObject.addProperty("z", src.getCornerTwo().getZ());
        jsonObject.add("cornerTwo", yObject);

        return jsonObject;
    }

    @Override
    public Area deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonObject xObject = jsonObject.getAsJsonObject("cornerOne");
        Location x = new Location(
                Bukkit.getWorld(xObject.get("world").getAsString()),
                xObject.get("x").getAsDouble(),
                xObject.get("y").getAsDouble(),
                xObject.get("z").getAsDouble()
        );

        JsonObject yObject = jsonObject.getAsJsonObject("cornerTwo");
        Location y = new Location(
                Bukkit.getWorld(yObject.get("world").getAsString()),
                yObject.get("x").getAsDouble(),
                yObject.get("y").getAsDouble(),
                yObject.get("z").getAsDouble()
        );

        return new Area(x, y);
    }
}
