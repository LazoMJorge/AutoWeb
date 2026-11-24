package org.example.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.Pages.CommonPage;
import org.example.Pages.PaginaInicio;
import org.example.Tasks.RegistrarTask;
import org.example.Utils.PreAndPostCondition;
import org.example.Utils.ScreenShot;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class RegistroStepDefinition {

    @Given("el {actor} abre la pagina principal de la tienda")
    public void abrirPaginaPrincipal(Actor actor) {
        actor.attemptsTo(Open.browserOn().the(PaginaInicio.class));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Inicio_Tienda");
    }

    @Given("el {actor} tiene una cuenta nueva con sesion activa:")
    public void tenerCuentaNuevaConSesionActiva(Actor actor, Map<String, String> datos) {
        actor.attemptsTo(RegistrarTask.registrarCuenta(datos));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Cuenta_Activa");
    }

    @When("el {actor} accede al formulario de nuevo registro")
    public void accederFormularioRegistro(Actor actor) {
        actor.attemptsTo(
                Click.on(PaginaInicio.BTN_LOGIN_O_REGISTRO),
                Click.on(CommonPage.BTN_CONTINUAR)
        );
    }

    @And("completa el formulario de alta con los datos personales:")
    public void completarFormularioAltaConTabla(Map<String, String> datos) {
        theActorInTheSpotlight().attemptsTo(RegistrarTask.conDatos(datos));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Formulario_Registro");
    }

    @And("completa el formulario de alta omitiendo el campo {string}:")
    public void completarFormularioAltaOmitiendoCampo(String campo, Map<String, String> datos) {
        theActorInTheSpotlight().attemptsTo(RegistrarTask.conDatosOmitiendo(datos, campo));
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Formulario_Incompleto");
    }

    @And("intenta crear la cuenta")
    public void intentarCrearLaCuenta() {
        theActorInTheSpotlight().attemptsTo(
                Click.on(CommonPage.BTN_CONTINUAR),
                WaitUntil.the(CommonPage.ALERTA_ERROR, WebElementStateMatchers.isVisible()).forNoMoreThan(10).seconds()
        );
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Registro_Rechazado");
    }

    @And("confirma la creacion de la cuenta")
    public void confirmarCreacionCuenta() {
        theActorInTheSpotlight().attemptsTo(
                Click.on(CommonPage.BTN_CONTINUAR),
                WaitUntil.the(CommonPage.LBL_TITULO_PRINCIPAL, WebElementStateMatchers.isVisible()).forNoMoreThan(15).seconds()
        );
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Cuenta_Creada");
    }

    @Then("el titulo principal de la pagina debe ser {string}")
    public void validarTituloPrincipal(String tituloEsperado) {
        theActorInTheSpotlight().should(
                seeThat(actor -> Text.of(CommonPage.LBL_TITULO_PRINCIPAL).answeredBy(actor).trim(), equalTo(tituloEsperado))
        );
    }

    @Then("se muestra la alerta de error {string}")
    public void validarAlertaDeError(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat(actor -> Text.of(CommonPage.ALERTA_ERROR).answeredBy(actor).replace("×", "").trim(), containsString(mensajeEsperado))
        );
    }

    @And("la cuenta no fue creada")
    public void validarCuentaNoCreada() {
        theActorInTheSpotlight().should(
                seeThat(actor -> Text.of(CommonPage.LBL_TITULO_PRINCIPAL).answeredBy(actor).trim(),
                        not(containsString("ACCOUNT HAS BEEN CREATED")))
        );
    }
}
