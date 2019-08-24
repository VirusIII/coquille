package sevsu.ru.coquille;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.app.Activity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

/**
 * This is a basic example that displays a map and sets camera focus on the target location.
 * Note: When working on your projects, remember to request the required permissions.
 */
public class MapActivity extends Activity implements UserLocationObjectListener {
    /**
     * Replace "your_api_key" with a valid developer key.
     * You can get it at the https://developer.tech.yandex.ru/ website.
     */
    private final String MAPKIT_API_KEY = "0ed8598d-d8a4-4d22-9fff-e8418c2f1fde";
    private Point TARGET_LOCATION = new Point(44.610926, 33.494000);

    private MapView mapView;
    private UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
        * Set the api key before calling initialize on MapKitFactory.
        * It is recommended to set api key in the Application.onCreate method,
        * but here we do it in each activity to make examples isolated.
        */
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        /**
        * Initialize the library to load required native libraries.
        * It is recommended to initialize the MapKit library in the Activity.onCreate method
        * Initializing in the Application.onCreate method may lead to extra calls and increased battery use.
        */
        MapKitFactory.initialize(this);
        // Now MapView can be created.
        setContentView(R.layout.map);
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.mapview);

        mapView.getMap().move(
                //userLocationLayer.cameraPosition()==null?userLocationLayer.cameraPosition():new CameraPosition(TARGET_LOCATION, 11.1f, 0.0f, 0.0f),
                new CameraPosition(TARGET_LOCATION, 11.1f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        userLocationLayer = mapView.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
//        TARGET_LOCATION =  userLocationLayer.cameraPosition().getTarget();
//        mapView.getMap().move(
//                new CameraPosition(TARGET_LOCATION, 11.1f, 0.0f, 0.0f),
//                new Animation(Animation.Type.SMOOTH, 5),
//                null);
    }

    @Override
    protected void onStop() {
        // Activity onStop call must be passed to both MapView and MapKit instance.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));
        userLocationView.getAccuracyCircle().setFillColor(Color.GREEN);
    }
    @Override
    public void onObjectRemoved(UserLocationView view) {
    }
    @Override
    public void onObjectUpdated(UserLocationView view, ObjectEvent event) {
    }

}
