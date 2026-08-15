# Proyecto-final-POO-2026
# Proyecto: Juego de pelea por turnos - titulo

## 1. Integrantes del Equipo 

- Fuertes, Nicolas
- Ojeda, Lautaro 
- Ojeda, Tomas
- Pereyra, Mariano

## 2. Dominio y Alcance del Sistema 

### Descripción del Problema
Se busca desarrollar una aplicación de escritorio del género de videojuegos **RPG por turnos**. El jugador deberá hacer uso de su repertorio de habilidades para debilitar a los enemigos que se presentarán de forma aleatoria u obligatoria. Los enemigos también tendrán sus respectivas habilidades que lanzarán al jugador, el objetivo será pasar a través de ellos ganando o huyendo de la batalla hasta llegar a la meta final del nivel.

### Objetivo del Sistema
El objetivo es desarrollar un videojuego funcional, donde el jugador pueda interactuar con un sistema de combate táctico. La arquitectura del sistema deberá ser modular y escalable para facilitar la futura incorporación de nuevas clases, habilidades y enemigos, basándose fuertemente en los pilares del paradigma orientado a objetos. 

### Funcionalidades Principales (Features)
- **Gestión de Equipo:**
    - El jugador puede seleccionar entre un compañero para que lo ayude durante las batallas.
    - El jugador puede elegir una clase al comenzar el juego.
    - Cada clase tiene ataques característicos únicos.
    - Cada compañero consta de vida, ataques y daño específico. 
- **Sistema de encuentros con Enemigos:**
    - Los enemigos comunes aparecen de manera aleatoria o en sectores ya establecidos.
    - Existen diferentes tipos de enemigos (ej: mucha vida pero poco daño, mucho daño pero poca vida).
- **Mecánicas de Juego:**
    - El jugador cuenta con un repertorio inicial de habilidades para atacar, que conforme vaya ganando experiencia se irá agrandando.
    - Derrotar enemigos otorgará experiencia y dinero.
    - Con el dinero el jugador podrá adquirir objetos que lo ayudarán en su misión.
    - Si el jugador es derrotado volverá al último punto de control, perdiendo el progreso adquirido hasta el momento.
- **Interfaz Gráfica (IGU):**
    - Panel de Batalla: Visualización de listado de habilidades, objetos utilizables y opción de escapar.
    - Selección de Equipo: Visualización de los posibles aliados, estadísticas y habilidades de los mismos.