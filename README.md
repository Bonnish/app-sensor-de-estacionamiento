# App Sensor de Estacionamiento
![Firebase](https://img.shields.io/badge/firebase-a08021?style=for-the-badge&logo=firebase&logoColor=ffcd34)![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)![Android Studio](https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white)  
Aplicación móvil desarrollada en Android Studio (Kotlin) que se comunica con un ESP32 para medir distancia y controlar un sensor ultrasónico en tiempo real.
Este proyecto fue realizado como parte de una evaluación del instituto INACAP, integrando hardware IoT, Firebase y Android.

# Funciones principales
## Inicio de sesión

La app valida email y contraseña desde Firebase Realtime Database en la ruta:
```
ESP32/Usuarios/
```
## Activar / desactivar sensor

El botón modifica el valor:
```
ESP32/sensorActivo
```

permitiendo encender o apagar el sensor del ESP32 desde la app.

## Ver distancia en tiempo real

Lectura continua del valor:
```
ESP32/distancia
```

Actualizado automáticamente mediante un ValueEventListener.

## Tecnologías utilizadas

Kotlin (Android)

Android Studio

Firebase Realtime Database

ESP32 + Sensor HC-SR04

## Estructura básica de Firebase
``````
ESP32
│
├── Usuarios
│   ├── 1
│   │   ├── email: "demo@gmail.com"
│   │   ├── password: "1234a"
│   │   └── usuario: "Demo1"
│   └── 2
│       ├── email: "demo2@gmail.com"
│       ├── password: "123a"
│       └── usuario: "Demo2"
│
├── distancia: 5.26
└── sensorActivo: false
```
