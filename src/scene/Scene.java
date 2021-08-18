package scene;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

public class Scene {

    public String name;

    public Color background = Color.BLACK;

    public AmbientLight ambientLight = new AmbientLight();

    public List<LightSource> lights = new LinkedList<>();

    public Geometries geometries;

    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    public Scene setBackground(Color color) {
        if (color == null) return this;
        this.background = color;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(LightSource... lights) {
        this.lights = List.of(lights);
        return this;
    }

    public Scene createHierarchy() {
        setGeometries(geometries.createHierarchy());
        return this;
    }

}
