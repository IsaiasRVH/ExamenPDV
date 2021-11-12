package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    Node venus_node = new Node();
    Node earth_node = new Node();
    Node mars_node = new Node();
    Node jupit_node = new Node();
    Node sun_node = new Node();
    
    Spatial sun_s;
    Spatial venus_s;
    Spatial earth_s;
    Spatial mars_s;
    Spatial jupit_s;
    
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true); //Creamos el objeto para controlar las especificaciones
        settings.setTitle("Examen: Rotación y Translación de 4 Planetas"); //Cambiamos el nombre de la ventana 
        settings.setResolution(1280, 720);
        Main app = new Main();
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        //Se crean las esferas para los planetas
        Sphere sun = new Sphere(20, 20, 20);
        Sphere venus = new Sphere(20,20,1.5f);
        Sphere earth = new Sphere(20,20,3);
        Sphere mars = new Sphere(20,20,2);
        Sphere jupiter = new Sphere(20,20,7);
        
        //Usando las esferas previamente creadas se crean sus geometrias
        Geometry sun_geom = new Geometry("sun", sun);
        Geometry venus_geom = new Geometry("venus", venus);
        Geometry earth_geom = new Geometry("earth", earth);
        Geometry mars_geom = new Geometry("mars", mars);
        Geometry jupit_geom = new Geometry("jupiter", jupiter);
        
        
        //Se crean los materiales para los planetas
        Material sun_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material venus_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material earth_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material mars_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material jupit_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        
        //A cada material se le carga su respectiva textura para cada material
        sun_mat.setTexture("ColorMap", assetManager.loadTexture("Textures/sun_surface.jpg"));
        venus_mat.setTexture("ColorMap", assetManager.loadTexture("Textures/venus_surface.jpg"));
        earth_mat.setTexture("ColorMap", assetManager.loadTexture("Textures/earth_surface.jpg"));
        mars_mat.setTexture("ColorMap", assetManager.loadTexture("Textures/mars_surface.jpg"));
        jupit_mat.setTexture("ColorMap", assetManager.loadTexture("Textures/jupiter_surface.jpg"));
        
        
        //El material se le asigna a la geometria
        sun_geom.setMaterial(sun_mat);
        venus_geom.setMaterial(venus_mat);
        earth_geom.setMaterial(earth_mat);
        mars_geom.setMaterial(mars_mat);
        jupit_geom.setMaterial(jupit_mat);
       
        //Se mueven los planetas en el eje x para que estos no se sobrepongan
        venus_geom.setLocalTranslation(60, 0, 0);
        earth_geom.setLocalTranslation(80, 0, 0);
        mars_geom.setLocalTranslation(120, 0, 0);
        jupit_geom.setLocalTranslation(180, 0, 0);
        //Tambien se mueve la camara para que se aprecien todos los planetas
        cam.setLocation(new Vector3f(0,0,300));
        
        //Como las texturas cargan giradas se tiene que rotar las geometrias
        sun_geom.rotate(FastMath.DEG_TO_RAD*-90, 0, 0);
        sun_geom.rotate(0,FastMath.DEG_TO_RAD*15,0);
        venus_geom.rotate(FastMath.DEG_TO_RAD*-90, 0, 0);
        venus_geom.rotate(0,FastMath.DEG_TO_RAD*20,0);
        earth_geom.rotate(FastMath.DEG_TO_RAD*-90, 0, 0);
        earth_geom.rotate(0,FastMath.DEG_TO_RAD*15,0);
        mars_geom.rotate(FastMath.DEG_TO_RAD*-90, 0, 0);
        mars_geom.rotate(0,FastMath.DEG_TO_RAD*15,0);
        jupit_geom.rotate(FastMath.DEG_TO_RAD*-90, 0, 0);
        jupit_geom.rotate(0,FastMath.DEG_TO_RAD*15,0);
        
        
        //Ya establecidas se creo el grafo de la escena donde el root node tiene como hijo al sun_node
        //El sun_node tiene como hijo a la geometria del sol y los nodos de los planetas
        //Cada nodo de los planetas tiene la geometria de su respectivo planeta como hijo
        /*
                                rootNode
                                    |
                            ____sun_node__________
                           /      /  \     \      \
                         /      /      \     \      sun_geom
                       /      /          \      \
               venus_node earth_node mars_node jupit_node
                    /       /           \           \
            venus_geom  earth_geom  mars_geom   jupit_geom
        */
        
        venus_node.attachChild(venus_geom);
        earth_node.attachChild(earth_geom);
        mars_node.attachChild(mars_geom);
        jupit_node.attachChild(jupit_geom);
        sun_node.attachChild(sun_geom);
        sun_node.attachChild(venus_node);
        sun_node.attachChild(earth_node);
        sun_node.attachChild(mars_node);
        sun_node.attachChild(jupit_node);
        rootNode.attachChild(sun_node);
        
        //Se configura la camara para que tenga una velocidad de 100
        //y pueda moverse mas rapido en la ejecución ya que por el 
        //tamaño la velocidad por defecto es muy baja
        flyCam.setMoveSpeed(100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //Se comprueba si los espaciales no estan inicializados
        //si esto se cumple se inicializan
        if(sun_s == null){
            sun_s = sun_node.getChild("sun");
        }
        if(venus_s == null){
            venus_s = venus_node.getChild("venus");
        }
        if(earth_s == null){
            earth_s = sun_node.getChild("earth");
        }
        if(mars_s == null){
            mars_s = mars_node.getChild("mars");
        }
        if(jupit_s == null){
            jupit_s = jupit_node.getChild("jupiter");
        }
        
        //Se rotan los planetas por medio de su espacial, cada uno con una
        //velocidad diferente
        sun_s.rotate(0,0,tpf);
        venus_s.rotate(0,0,tpf*5);
        earth_s.rotate(0,0,tpf*3.3f);
        mars_s.rotate(0,0,tpf*2);
        jupit_s.rotate(0,0  ,tpf);
        
        //Se rotan los nodos donde se encuentran los planetas, como cada uno
        //esta en un nodo diferente se pueden rotar a velocidades diferentes
        venus_node.rotate(0, 0.002f, 0);
        earth_node.rotate(0, 0.001f, 0);
        mars_node.rotate(0, 0.00025f, 0);
        jupit_node.rotate(0,0.00005f, 0);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}