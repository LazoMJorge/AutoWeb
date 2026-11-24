package org.example.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Value;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.Pages.CommonPage;
import org.example.Pages.PaginaCompra;
import org.example.Questions.TiendaQuestions;
import org.example.Tasks.ComprarTask;
import org.example.Utils.PreAndPostCondition;
import org.example.Utils.ScreenShot;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;

public class CompraStepDefinition {

    @When("el {actor} busca el producto {string} desde el buscador global")
    public void buscarProductoEnTienda(Actor actor, String producto) {
        actor.attemptsTo(ComprarTask.buscarProducto(producto));
    }

    @And("configura la cantidad en {string} unidades y lo agrega al carrito")
    public void configurarCantidadYAgregarAlCarrito(String cantidad) {
        theActorInTheSpotlight().attemptsTo(ComprarTask.agregarProductoConCantidad(cantidad));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Producto_En_Carrito");
    }

    @Then("el carrito de compras contiene el producto {string} con cantidad {string}")
    public void validarProductoYCantidadEnCarrito(String producto, String cantidadEsperada) {
        theActorInTheSpotlight().should(
                seeThat(TiendaQuestions.nombresProductosCarrito(), hasItem(producto)),
                seeThat(actor -> Value.of(PaginaCompra.campoCantidadPara(producto)).answeredBy(actor).trim(), equalTo(cantidadEsperada))
        );
    }

    @When("el {actor} actualiza la cantidad de {string} a {string} en el carrito")
    public void actualizarCantidadEnCarrito(Actor actor, String producto, String cantidad) {
        actor.attemptsTo(ComprarTask.actualizarCantidadEnCarrito(producto, cantidad));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Cantidad_Actualizada");
    }

    @When("el {actor} elimina el producto {string} del carrito")
    public void eliminarProductoDelCarrito(Actor actor, String producto) {
        actor.attemptsTo(ComprarTask.eliminarProductoDelCarrito(producto));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Producto_Eliminado");
    }

    @Then("el carrito de compras ya no contiene el producto {string}")
    public void validarProductoEliminadoDelCarrito(String producto) {
        theActorInTheSpotlight().should(
                seeThat(TiendaQuestions.nombresProductosCarrito(), not(hasItem(producto)))
        );
    }

    @When("el {actor} avanza al resumen de checkout")
    public void avanzarAlResumenDeCheckout(Actor actor) {
        actor.attemptsTo(
                Click.on(PaginaCompra.BTN_CHECKOUT),
                WaitUntil.the(PaginaCompra.BTN_CONFIRMAR_ORDEN, WebElementStateMatchers.isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @When("el {actor} confirma la orden de compra")
    public void confirmarOrdenDeCompra(Actor actor) {
        actor.attemptsTo(ComprarTask.confirmarCompra());
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Orden_Procesada");
    }

    @And("se muestra el numero de orden generado en el panel de confirmacion")
    public void validarNumeroDeOrdenGenerado() {
        Actor actor = theActorInTheSpotlight();
        String numeroOrden = TiendaQuestions.numeroOrdenGenerado().answeredBy(actor);
        actor.remember("numeroOrden", numeroOrden);
        actor.should(
                seeThat(a -> Text.of(CommonPage.PNL_CONTENIDO).answeredBy(a).trim(), containsString("Your order #")),
                seeThat(TiendaQuestions.numeroOrdenGenerado(), not(equalTo("")))
        );
    }

    @When("el {actor} consulta el detalle de la orden")
    public void consultarDetalleOrden(Actor actor) {
        actor.attemptsTo(
                Click.on(PaginaCompra.LNK_DETALLE_ORDEN),
                WaitUntil.the(PaginaCompra.TBL_PRODUCTOS_ORDEN, WebElementStateMatchers.isVisible()).forNoMoreThan(10).seconds()
        );
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Detalle_Orden");
    }

    @Then("el detalle de la orden muestra el producto {string} con cantidad {string}")
    public void validarProductoYCantidadEnDetalleOrden(String producto, String cantidad) {
        Actor actor = theActorInTheSpotlight();
        String numeroOrden = actor.recall("numeroOrden");
        actor.should(
                seeThat("Numero de orden en el detalle", a -> Text.of(CommonPage.PNL_CONTENIDO).answeredBy(a).trim(), containsString(numeroOrden)),
                seeThat("Estado pendiente en el detalle de la orden", a -> Text.of(CommonPage.PNL_CONTENIDO).answeredBy(a).trim(), containsString("Pending")),
                seeThat("Total en el detalle de la orden", a -> Text.of(CommonPage.PNL_CONTENIDO).answeredBy(a).trim(), containsString("Total:")),
                seeThat("Producto en el detalle de la orden", a -> Text.of(PaginaCompra.TBL_PRODUCTOS_ORDEN).answeredBy(a).trim(), containsString(producto)),
                seeThat("Cantidad en el detalle de la orden", a -> Text.of(PaginaCompra.TBL_PRODUCTOS_ORDEN).answeredBy(a).trim(), containsString(cantidad))
        );
    }
}
