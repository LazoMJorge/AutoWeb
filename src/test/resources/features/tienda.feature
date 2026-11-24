@Regresion @TiendaWeb
Feature: Registro, inicio de sesion y compra en AutomationTestStore

  Como cliente de la tienda virtual de practica AutomationTestStore
  Quiero registrarme, iniciar sesion y comprar productos
  Para realizar compras en la web

  Background:
    Given el usuario abre la pagina principal de la tienda

  @CP01 @Registro @HappyPath
  Scenario: Registro completo de un nuevo cliente
    When el usuario accede al formulario de nuevo registro
    And completa el formulario de alta con los datos personales:
      | nombre    | Jorge                          |
      | apellido  | Lazo Moscoso                   |
      | email     | jorgelazo_qa_demo@example.test |
      | telefono  | 000111222                      |
      | direccion | Calle Ficticia 123 Demo        |
      | ciudad    | Arequipa                       |
      | pais      | Peru                           |
      | region    | Arequipa                       |
      | cp        | 04000                          |
      | usuario   | jorgelazo_qa_demo              |
      | clave     | DemoTest123!                   |
    And confirma la creacion de la cuenta
    Then el titulo principal de la pagina debe ser "YOUR ACCOUNT HAS BEEN CREATED!"

  @CP02 @Registro @UnhappyPath
  Scenario Outline: Rechazo del registro cuando falta el campo <campo>
    When el usuario accede al formulario de nuevo registro
    And completa el formulario de alta omitiendo el campo "<campo>":
      | nombre    | Jorge                          |
      | apellido  | Lazo Moscoso                   |
      | email     | jorgelazo_qa_demo@example.test |
      | telefono  | 000111222                      |
      | direccion | Calle Ficticia 123 Demo        |
      | ciudad    | Arequipa                       |
      | pais      | Peru                           |
      | region    | Arequipa                       |
      | cp        | 04000                          |
      | usuario   | jorgelazo_qa_demo              |
      | clave     | DemoTest123!                   |
    And intenta crear la cuenta
    Then se muestra la alerta de error "<mensaje>"
    And la cuenta no fue creada

    Examples:
      | campo   | mensaje                                                               |
      | nombre  | First Name must be between 1 and 32 characters!                       |
      | email   | Email Address does not appear to be valid!                            |
      | usuario | Login name must be alphanumeric only and between 5 and 64 characters! |
      | clave   | Password must be between 4 and 20 characters!                         |

  @CP03 @Login @HappyPath
  Scenario: Inicio de sesion con una cuenta registrada
    Given el usuario tiene una cuenta nueva con sesion activa:
      | nombre    | Jorge                          |
      | apellido  | Lazo Moscoso                   |
      | email     | jorgelazo_qa_demo@example.test |
      | telefono  | 000111222                      |
      | direccion | Calle Ficticia 123 Demo        |
      | ciudad    | Arequipa                       |
      | pais      | Peru                           |
      | region    | Arequipa                       |
      | cp        | 04000                          |
      | usuario   | jorgelazo_qa_demo              |
      | clave     | DemoTest123!                   |
    When el usuario cierra la sesion
    And el usuario accede a la seccion de autenticacion
    And inicia sesion con las credenciales registradas
    Then el titulo principal de la pagina debe ser "MY ACCOUNT"

  @CP04 @Login @UnhappyPath
  Scenario Outline: Rechazo del inicio de sesion con el usuario <usuario>
    When el usuario accede a la seccion de autenticacion
    And inicia sesion con el usuario "<usuario>" y la clave "<clave>"
    Then se muestra la alerta de error "Error: Incorrect login or password provided."

    Examples:
      | usuario                | clave             |
      | usuario_inexistente_qa | ClaveInvalida123! |
      | otro_usuario_falso     | 123456            |

  @CP05 @Compra @HappyPath
  Scenario: Busqueda de un producto y compra completa
    Given el usuario tiene una cuenta nueva con sesion activa:
      | nombre    | Jorge                          |
      | apellido  | Lazo Moscoso                   |
      | email     | jorgelazo_qa_demo@example.test |
      | telefono  | 000111222                      |
      | direccion | Calle Ficticia 123 Demo        |
      | ciudad    | Arequipa                       |
      | pais      | Peru                           |
      | region    | Arequipa                       |
      | cp        | 04000                          |
      | usuario   | jorgelazo_qa_demo              |
      | clave     | DemoTest123!                   |
    When el usuario busca el producto "Skinsheen Bronzer Stick" desde el buscador global
    And configura la cantidad en "1" unidades y lo agrega al carrito
    Then el carrito de compras contiene el producto "Skinsheen Bronzer Stick" con cantidad "1"
    When el usuario actualiza la cantidad de "Skinsheen Bronzer Stick" a "2" en el carrito
    Then el carrito de compras contiene el producto "Skinsheen Bronzer Stick" con cantidad "2"
    When el usuario busca el producto "Eau Parfumee au The Vert Shampoo" desde el buscador global
    And configura la cantidad en "1" unidades y lo agrega al carrito
    Then el carrito de compras contiene el producto "Eau Parfumee au The Vert Shampoo" con cantidad "1"
    When el usuario elimina el producto "Eau Parfumee au The Vert Shampoo" del carrito
    Then el carrito de compras ya no contiene el producto "Eau Parfumee au The Vert Shampoo"
    And el carrito de compras contiene el producto "Skinsheen Bronzer Stick" con cantidad "2"
    When el usuario avanza al resumen de checkout
    And el usuario confirma la orden de compra
    Then el titulo principal de la pagina debe ser "YOUR ORDER HAS BEEN PROCESSED!"
    And se muestra el numero de orden generado en el panel de confirmacion
    When el usuario consulta el detalle de la orden
    Then el detalle de la orden muestra el producto "Skinsheen Bronzer Stick" con cantidad "2"
