# AndroidViews – Tema Oscuro y Mapa (OpenStreetMap)

## Resumen de cambios
- Implementado conmutador de tema oscuro en Ajustes.
- Integrado mapa basado en OpenStreetMap usando osmdroid en la pantalla de Inicio.
- Persistencia de preferencia de tema y aplicación del tema al iniciar la app.
- Añadidos permisos de red para descarga de mosaicos del mapa.

## Herramientas y librerías
- Material 3 + DayNight para temas: `Theme.Material3.DayNight.NoActionBar` (ya presente).
- Navigation Component (ya presente).
- Lifecycle ViewModel + LiveData (ya presente).
- OpenStreetMap (osmdroid): `org.osmdroid:osmdroid-android:6.1.18`.

## Arquitectura: gestión de Fragments y navegación
- **Activity contenedora**: `MainActivity` aloja un `NavHostFragment` (`R.id.nav_host_fragment`).
- **Bottom navigation**: `BottomNavigationView` enlazado con `NavController` mediante `NavigationUI.setupWithNavController(...)`.
- **AppBar**: `NavigationUI.setupActionBarWithNavController(...)` para manejar el botón Up.
- **Visibilidad condicional**: se oculta el bottom nav en `loginFragment` con `addOnDestinationChangedListener`.
- **Gráfico de navegación**: `res/navigation/nav_graph.xml` define destinos y `startDestination` (`loginFragment`) y la acción `action_login_to_home`.
- **Fragments principales**: `HomeFragment`, `DashboardFragment`, `ProcessFragment`, `SettingsFragment`, más `LoginFragment`.

## Archivos modificados / creados
- app/build.gradle
  - Agregada dependencia de osmdroid.
- app/src/main/AndroidManifest.xml
  - Agregados permisos: `INTERNET`, `ACCESS_NETWORK_STATE`.
- app/src/main/res/layout/fragment_settings.xml
  - Reemplazado por layout con `Switch` para tema oscuro.
- app/src/main/java/com/example/androidviews/ui/settings/SettingsFragment.java
  - Lógica de toggle de tema, persistencia con `SharedPreferences`, y aplicación vía `AppCompatDelegate`.
- app/src/main/res/layout/fragment_home.xml
  - Reemplazado por layout con `org.osmdroid.views.MapView`.
- app/src/main/java/com/example/androidviews/ui/home/HomeFragment.java
  - Inicialización y configuración del `MapView`, user agent, centro y zoom; manejo de ciclo de vida `onResume/onPause`.
- app/src/main/java/com/example/androidviews/ui/MainActivity.java
  - Aplicación del tema guardado antes de `setContentView`.
- README.md (este archivo)

## Detalles de implementación
### Tema Oscuro
- Preferencia guardada en `SharedPreferences` (archivo `app_prefs`, clave `dark_mode`).
- `SettingsFragment` actualiza la preferencia y cambia el modo nocturno con `AppCompatDelegate.setDefaultNightMode(...)`, recreando la actividad para aplicar cambios.
- `MainActivity` lee la preferencia al iniciar y establece el modo antes de llamar a `setContentView`.

#### Pasos de implementación
1. Layout `fragment_settings.xml`: añadir un `Switch` con id `switchDarkTheme`.
2. `SettingsFragment`:
   - Leer estado guardado de `dark_mode` y reflejarlo en el `Switch`.
   - En `onCheckedChange`, guardar el valor y llamar a `AppCompatDelegate.setDefaultNightMode(...)`.
   - Llamar a `requireActivity().recreate()` para aplicar el tema inmediatamente.
3. `MainActivity` (antes de `setContentView`): leer `dark_mode` y fijar el modo con `AppCompatDelegate` para que el tema se aplique desde el arranque.

### Mapa (OpenStreetMap con osmdroid)
- Layout `fragment_home.xml` contiene un `MapView` a pantalla completa.
- `HomeFragment`:
  - Configura el user agent: `Configuration.getInstance().setUserAgentValue(requireContext().getPackageName())` (recomendación de osmdroid).
  - Fuente de teselas: `TileSourceFactory.MAPNIK`.
  - Multitáctil habilitado.
  - Centro inicial (demo): Madrid (40.4168, -3.7038), zoom 5.
  - Manejo de ciclo de vida: `onResume()`/`onPause()`.

#### Pasos de implementación
1. Gradle (`app/build.gradle`): agregar dependencia `org.osmdroid:osmdroid-android:6.1.18`.
2. Manifest: agregar permisos `INTERNET` y `ACCESS_NETWORK_STATE`.
3. Layout `fragment_home.xml`: sustituir contenido por `org.osmdroid.views.MapView` a pantalla completa.
4. `HomeFragment`:
   - En `onViewCreated`, obtener el `MapView`, configurar tile source, multitouch, centro y zoom.
   - Establecer user agent de osmdroid con el package name.
   - Propagar ciclo de vida: llamar a `mapView.onResume()` en `onResume()` y `mapView.onPause()` en `onPause()`.

## Permisos
- `INTERNET` y `ACCESS_NETWORK_STATE` añadidos para descargar y gestionar mosaicos del mapa.
- No se requiere ubicación para visualizar el mapa básico. Si se desea mostrar "mi ubicación", se necesitarán permisos de localización (`ACCESS_FINE_LOCATION`/`ACCESS_COARSE_LOCATION`) y configurar un overlay de ubicación.

## Cómo ejecutar
1. Sincronizar Gradle (asegúrate de tener conexión a Internet para descargar dependencias y mosaicos del mapa).
2. Compilar y ejecutar en un dispositivo/emulador con Android compatible (minSdk y compileSdk según `libs.versions`).
3. Cambiar tema: Ir a Ajustes → activar/desactivar "Tema oscuro".
4. Ver mapa: Ir a Inicio → se muestra un mapa OSM.

## Posibles mejoras
- Agregar botón "mi ubicación" con permisos de ubicación y `MyLocationNewOverlay` de osmdroid.
- Guardar y restaurar estado del mapa (último centro/zoom).
- Añadir control de zoom y marcadores.
- Internacionalizar textos de Ajustes en `strings.xml`.

## Notas de compatibilidad
- La app usa `Theme.Material3.DayNight.NoActionBar`, por lo que el modo oscuro está soportado por defecto.
- osmdroid utiliza caché en almacenamiento de la app; en versiones modernas de Android no es necesario permiso de almacenamiento.
