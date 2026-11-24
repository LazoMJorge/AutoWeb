# Automatización de Pruebas Web - AutomationTestStore

Este proyecto automatiza pruebas funcionales de extremo a extremo sobre la tienda virtual de práctica [AutomationTestStore](https://automationteststore.com/). Simula los pasos reales de un cliente en el navegador Chrome usando **Java, Serenity BDD (patrón Screenplay), Cucumber y Selenium WebDriver**.

## Descripción general

La suite verifica que los tres flujos principales de la tienda funcionen correctamente tanto cuando todo sale bien como cuando el sistema debe rechazar una acción:

- **Registro de clientes (`@Registro`)**: comprueba que una persona nueva pueda crear su cuenta con datos válidos y que la tienda rechace el registro si falta alguno de los campos obligatorios (`nombre`, `email`, `usuario` o `clave`), mostrando la alerta correspondiente.
- **Inicio de sesión (`@Login`)**: crea una cuenta nueva, cierra la sesión de forma explícita y vuelve a ingresar con esas credenciales para comprobar el acceso; también verifica que la tienda bloquee el ingreso con usuarios inexistentes o contraseñas incorrectas.
- **Compra de productos (`@Compra`)**: con la sesión iniciada, busca productos en el catálogo, agrega unidades al carrito, modifica cantidades, elimina un producto agregado de más, completa el proceso de compra y revisa que la orden generada muestre el número de pedido, su estado, el total y el producto comprado.

## Cómo está organizado el proyecto

El código separa qué elementos hay en cada pantalla, qué acciones realiza el usuario y qué resultados se comprueban:

- **Pantallas (`src/main/java/org/example/Pages`)**: reúne los elementos visibles de cada sección de la tienda (`PaginaInicio`, `PaginaLogin`, `PaginaRegistro`, `PaginaCompra` y `CommonPage` para elementos compartidos como títulos, botones de continuar y alertas).
- **Acciones (`src/main/java/org/example/Tasks`)**: agrupa los flujos de usuario en tareas claras (`RegistrarTask`, `IniciarSesionTask` y `ComprarTask`).
- **Consultas (`src/main/java/org/example/Questions`)**: `TiendaQuestions` lee información de la pantalla para validarla, como los productos en el carrito o el número de orden generado.
- **Evidencias y preparación (`src/main/java/org/example/Utils`)**:
  - `PreAndPostCondition` prepara cada escenario antes de empezar, crea su carpeta de evidencias y toma una captura automática (`FALLO`) si el escenario llega a fallar.
  - `ScreenShot` guarda las capturas de pantalla en formato PNG con fecha y hora.
  - `Parameters` define la carpeta base donde se almacenan las evidencias.
- **Pasos y ejecución (`src/test/java/org/example`)**: `stepDefinition/` conecta las frases en lenguaje natural con las acciones del navegador y toma capturas en los momentos clave; `RunnerTest.java` es el punto de entrada para lanzar toda la suite.
- **Escenarios y configuración (`src/test/resources`)**: contiene el archivo `features/tienda.feature` con los escenarios escritos en lenguaje Gherkin, además de `serenity.conf` y `logback-test.xml`.

## Requisitos previos

- Java (JDK 17 o superior)
- Apache Maven 3.6 o superior
- Navegador Google Chrome instalado
- Conexión a internet para acceder a la tienda de práctica

## Cómo ejecutar las pruebas y ver los resultados

Puedes lanzar toda la suite desde la clase **`RunnerTest`** en tu IDE (haciendo clic en **Run**) o desde la terminal con Maven:

```bash
mvn clean verify
```

> **Nota:** en este proyecto `mvn test` no ejecuta escenarios porque el plugin `surefire` está desactivado; la ejecución completa y la generación del reporte se hacen siempre con `mvn clean verify`.

Si quieres correr solo un flujo o un escenario específico, puedes filtrar por etiqueta:

```bash
mvn clean verify "-Dcucumber.filter.tags=@Registro"
mvn clean verify "-Dcucumber.filter.tags=@Login"
mvn clean verify "-Dcucumber.filter.tags=@Compra"
```

Al finalizar, encontrarás dos tipos de resultados:
- **Reporte interactivo de Serenity**: abre `target/site/serenity/index.html` en tu navegador para ver el resumen de los escenarios, pasos ejecutados y tiempos.
- **Capturas de pantalla**: se guardan organizadas por fecha y nombre de escenario dentro de la carpeta `evidencias/`.

## Decisiones de diseño

- **Datos nuevos en cada corrida**: cada vez que se registra un usuario, se genera un sufijo único para el nombre de usuario y el correo, lo que permite repetir las pruebas sin que la tienda rechace el registro por duplicado.
- **Escenarios independientes**: tanto el inicio de sesión como la compra crean su propia cuenta al empezar, evitando que las pruebas fallen si la tienda de práctica borra cuentas antiguas.
- **Sincronización con la página**: en lugar de usar pausas fijas de tiempo, las pruebas esperan a que aparezcan o cambien los elementos reales en pantalla (como la lista de regiones al elegir el país, la actualización de cantidad en el carrito o la confirmación de la orden).
