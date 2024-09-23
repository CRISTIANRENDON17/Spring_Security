# Spring Boot Security Demo

Este es un proyecto básico creado con **Spring Boot** y **Spring Security** para demostrar cómo configurar la seguridad en una aplicación web. Está diseñado para ser utilizado en una capacitación de la empresa y cubre conceptos importantes como la protección CSRF, autenticación basada en formularios, administración de sesiones y más.

## Tabla de Contenidos
- [Descripción](#descripción)
- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración de Seguridad](#configuración-de-seguridad)
- [Endpoints](#endpoints)
- [Ejecución](#ejecución)
- [Notas Importantes](#notas-importantes)
- [Licencia](#licencia)

## Descripción

Este proyecto muestra cómo implementar la seguridad en una aplicación Spring Boot usando Spring Security. Incluye ejemplos de cómo proteger endpoints, gestionar sesiones de usuario, y personalizar la autenticación utilizando un manejador de éxito de autenticación.

## Características

- Configuración de seguridad con Spring Security.
- Protección de endpoints usando autenticación basada en formularios y autenticación básica HTTP.
- Desactivación de la protección CSRF para un entorno más adecuado a APIs RESTful.
- Gestión de sesiones y límites de sesiones activas por usuario.
- Personalización del comportamiento tras el inicio de sesión exitoso.

## Requisitos Previos

- **Java 19** o superior
- **Maven** o **Gradle** (según tu preferencia de compilación)
- **Spring Boot 3.1.0** (o versión compatible)

## Estructura del Proyecto

```plaintext
src/main/java/com/demo/SpringSecurity
│
├── Configs
│   └── SecurityConfig.java       # Configuración de seguridad con Spring Security
│
├── Controllers
│   └── CustomerController.java   # Controlador que define los endpoints de la aplicación
│
└── Application.java              # Clase principal para ejecutar el proyecto Spring Boot

## Configuración de Seguridad

El proyecto cuenta con dos configuraciones principales de seguridad:

### 1. Desactivación de CSRF
Dado que es común desactivarlo en aplicaciones que exponen servicios RESTful, se ha desactivado la protección CSRF para simplificar la seguridad y evitar errores innecesarios.

### 2. Autenticación y Autorización
- **Endpoints Públicos**: Solo el endpoint `/v1/index2` es público; el resto requiere autenticación.
- **Métodos de Autenticación**: La autenticación se realiza mediante un formulario o autenticación HTTP Basic.

### 3. Gestión de Sesiones
- **Número de Sesiones Activas**: Se permite solo una sesión activa por usuario.
- **Redirección en Sesiones Inválidas o Expiradas**: Se redirige a `/login` en caso de que una sesión sea inválida o haya expirado.

## Configuración del `SecurityConfig.java`

Este archivo contiene la lógica principal de la configuración de seguridad, incluyendo la administración de sesiones y la autenticación basada en formulario y HTTP Basic.

### Manejador de Éxito Personalizado
Se define un `AuthenticationSuccessHandler` que redirige al endpoint `/v1/index` tras una autenticación exitosa.

### Configuración del Registro de Sesiones
Se incluye un `SessionRegistry` que ayuda a gestionar y monitorear las sesiones activas de los usuarios.


## Configuración del `SecurityConfig.java`

Este archivo contiene la lógica principal de la configuración de seguridad, incluyendo la administración de sesiones y la autenticación basada en formulario y HTTP Basic.

### Manejador de Éxito Personalizado

Se define un `AuthenticationSuccessHandler` que redirige al endpoint `/v1/index` tras una autenticación exitosa.

### Configuración del Registro de Sesiones

Se incluye un `SessionRegistry` que ayuda a gestionar y monitorear las sesiones activas de los usuarios.

## Endpoints

| Método | Endpoint       | Descripción                           | Seguridad          |
|--------|----------------|---------------------------------------|---------------------|
| GET    | `/v1/index`    | Devuelve un mensaje de bienvenida     | Protegido (seguro)  |
| GET    | `/v1/index2`   | Devuelve un mensaje sin seguridad     | No protegido        |
| GET    | `/v1/session`  | Muestra detalles de las sesiones activas | Protegido (seguro)  |



Ejecución
Clona el repositorio:
git clone https://github.com/CRISTIANRENDON17/Spring_Security.git


Compila y ejecuta la aplicación:
mvn spring-boot:run


Accede a la aplicación en tu navegador:
Copiar código
http://localhost:8415/v1/index
