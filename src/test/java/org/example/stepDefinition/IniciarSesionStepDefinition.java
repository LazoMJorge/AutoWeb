package org.example.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.Pages.CommonPage;
import org.example.Pages.PaginaInicio;
import org.example.Tasks.IniciarSesionTask;
import org.example.Utils.PreAndPostCondition;
import org.example.Utils.ScreenShot;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class IniciarSesionStepDefinition {

    @When("el {actor} cierra la sesion")
    public void cerrarSesion(Actor actor) {
        actor.attemptsTo(
                Click.on(PaginaInicio.BTN_CERRAR_SESION),
                WaitUntil.the(PaginaInicio.BTN_LOGIN_O_REGISTRO, WebElementStateMatchers.isVisible()).forNoMoreThan(10).seconds()
        );
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Sesion_Cerrada");
    }

    @When("el {actor} accede a la seccion de autenticacion")
    public void accederSeccionAutenticacion(Actor actor) {
        actor.attemptsTo(Click.on(PaginaInicio.BTN_LOGIN_O_REGISTRO));
    }

    @And("inicia sesion con las credenciales registradas")
    public void iniciarSesionConCredencialesRegistradas() {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesionTask.conCredencialesRecordadas(),
                WaitUntil.the(CommonPage.LBL_TITULO_PRINCIPAL, WebElementStateMatchers.containsText("MY ACCOUNT"))
                        .forNoMoreThan(10).seconds()
        );
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Login_Exitoso");
    }

    @And("inicia sesion con el usuario {string} y la clave {string}")
    public void iniciarSesionConCredencialesExplicitas(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesionTask.conCredenciales(usuario, clave),
                WaitUntil.the(CommonPage.ALERTA_ERROR, WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds()
        );
        ScreenShot.captureScreenshot(getDriver(), PreAndPostCondition.getScreenShotFolder(), "Login_Fallido");
    }
}
