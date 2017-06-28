package com.example.khangduyle.miniproject1412083;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khangduyle.miniproject1412083.ModuleDirection.DirectionFinder;
import com.example.khangduyle.miniproject1412083.ModuleDirection.DirectionFinderListener;
import com.example.khangduyle.miniproject1412083.ModuleDirection.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class mapActivity extends FragmentActivity implements OnMapReadyCallback,
        DirectionFinderListener,
        ClusterManager.OnClusterClickListener<MyItem>,
        ClusterManager.OnClusterInfoWindowClickListener<MyItem>,
        ClusterManager.OnClusterItemClickListener<MyItem>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {
    private GoogleMap mMap;
    Button btnHybrid;
    Button btnTerrain;
    Button btnSate;
    Button btnNormal;
    private ClusterManager<MyItem> mClusterManager;
    private View convertView;
    private OnInterInfoWindowTouchListener lsClick;
    private HashMap<String, MyItem> markerPersonMap = new HashMap<>();
    private EditText editTextOrigin;
    private EditText editTextDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private NavigationView mNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_wrapper);
        editTextOrigin = (EditText) findViewById(R.id.edit_text_origin);
        editTextDestination = (EditText) findViewById(R.id.edit_text_destination);

        mNav = (NavigationView) findViewById(R.id.nav);


    }

    private void NavBar() {

        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent goIntent;
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navList:
                        goIntent = new Intent(mapActivity.this, ListPlaceActivity.class);
                        startActivity(goIntent);
                        return true;
                    case R.id.navadd:
                        goIntent = new Intent(mapActivity.this, UpLoadPlaceActivity.class);
                        startActivity(goIntent);
                        return true;
                    case R.id.terrain:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        return true;
                    case R.id.hybrid:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        return true;
                    case R.id.satellite:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        return true;
                    case R.id.navNormal:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Places");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Place place=dataSnapshot.getValue(Place.class);
                Place place;
                mClusterManager.clearItems(); // clear to avoid duplicating

                Toast.makeText(mapActivity.this, "Read database", Toast.LENGTH_LONG).show();
                for (DataSnapshot mydata : dataSnapshot.getChildren()) {
                    place = mydata.getValue(Place.class);

                    MyItem offsetItem = new MyItem(place.mLat, place.mLng, "ahihi", R.drawable.store, place);
                    mClusterManager.addItem(offsetItem);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendRequest() {
        String origin = editTextOrigin.getText().toString();
        String destination = editTextDestination.getText().toString();

        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait", "Finding direction...", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }


    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.text_view_distance)).setText(route.distance.text);
            ((TextView) findViewById(R.id.text_view_time)).setText(route.duration.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));

            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions()
                    .geodesic(true)
                    .color(Color.BLUE)
                    .width(10);

            for (int i = 0; i < route.points.size(); i++) {
                polylineOptions.add(route.points.get(i));
            }

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    public void onClickBtnFindPath(View view) {
        sendRequest();
    }

    public void onClickBtnSwitch(View view) {
       /* String origin = editTextOrigin.getText().toString();
        String destination = editTextDestination.getText().toString();
        String tmp = "";
        if (origin.isEmpty() || destination.isEmpty()) {
            return;
        }

        tmp = origin;
        origin = destination;
        destination = tmp;

        editTextOrigin.setText(origin);
        editTextDestination.setText(destination);*/
        Intent intent = new Intent(mapActivity.this, UpLoadPlaceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mapWrapperLayout.init(mMap, this);
        NavBar();


        // Add a marker in Sydney and move the camera
       /* LatLng hcmus = new LatLng(10.763434, 106.682230);
        mMap.addMarker(new MarkerOptions().position(hcmus).title("Marker in University of Science HCMC")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18),2000,null);
        CameraPosition cameraPosition= new CameraPosition.Builder()
                .target(hcmus)
                .zoom(18).bearing(90).tilt(30).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));*/
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        setUpClusterer();

        mMap.setOnInfoWindowClickListener(mClusterManager);

        convertView = LayoutInflater.from(mapActivity.this).inflate(R.layout.content_location, null);




        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {return null; }

            @Override
            public View getInfoContents(Marker marker) {
                MyItem person = markerPersonMap.get(marker.getId());

                TextView name= (TextView)convertView.findViewById(R.id.nameLocation);
                TextView location = (TextView)convertView.findViewById(R.id.location);
                TextView email = (TextView)convertView.findViewById(R.id.email);

                name.setText(person.mPlace.mName); // gán tên địa điểm vào
                location.setText(person.mPlace.mAdd);
                email.setText(person.getTitle());

               // mapWrapperLayout.setMarkerWithInfoWindow(marker, convertView);
                return convertView;
            }
        });

        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        //addItems();
        mClusterManager.cluster();

    }

    @Override
    public boolean onClusterClick(Cluster<MyItem> cluster) {
        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().getTitle();
        Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {
        Toast.makeText(this,"onClusterInfoWindowClick",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onClusterItemClick(MyItem myItem) {
        Toast.makeText(this,"onClusterItemClick",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {
        Toast.makeText(this,"onClusterItemInfoWindowClick",Toast.LENGTH_LONG).show();
        MyItem person = myItem;
        Toast.makeText(mapActivity.this,"Ehiihihi",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mapActivity.this, PlaceDetailActivity.class );// not edit yet

        intent.putExtra("phoneNumber",person.mPlace.mNumber);
        intent.putExtra("name", person.mPlace.mName);
        intent.putExtra("email", person.mPlace.mEmail);
        intent.putExtra("idAvatar", person.mPlace.mImg );
        intent.putExtra("web",person.mPlace.mWebsite);
        intent.putExtra("Add",person.mPlace.mAdd);
        intent.putExtra("desc",person.mPlace.mDescription);
        startActivity(intent);
    }

    private class PersonRenderer extends DefaultClusterRenderer<MyItem> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        //private final ImageView mImageView;
        private final CircleImageView mImageView;
        private final ImageView mClusterImageView;
        private final int mDimension;

        public PersonRenderer() {
            super(getApplicationContext(), mMap, mClusterManager);

            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new CircleImageView(getApplicationContext()); //mImageView = new ImageView(getApplicationContext());

            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);

            mIconGenerator.setContentView(mImageView);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem person, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.

            mImageView.setImageResource(person.getImg());

           /* Picasso.with(mapActivity.this)
                    .load(person.mImg)
                    .into(mImageView);*/
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.getTitle());
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (MyItem p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = getResources().getDrawable(p.getImg());
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }

        @Override
        protected void onClusterItemRendered(MyItem person, Marker marker) {
            super.onClusterItemRendered(person, marker);
            markerPersonMap.put(marker.getId(), person);
        }
    }
    private void setUpClusterer() {
        // Position the map.
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, mMap);
        mClusterManager.setRenderer(new PersonRenderer());
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.

    }
/*
    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng,"ahihi",R.drawable.avatar);
            mClusterManager.addItem(offsetItem);
        }
    }
*/

}
