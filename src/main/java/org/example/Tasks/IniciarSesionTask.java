package org.example.Tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.example.Pages.PaginaLogin;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IniciarSesionTask implements Task {

    private final String usuario;
    private final String clave;

    public IniciarSesionTask() {
        this(null, null);
    }

    public IniciarSesionTask(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public static Performable conCredenciales(String usuario, String clave) {
        return instrumented(IniciarSesionTask.class, usuario, clave);
    }

    public static Performable conCredencialesRecordadas() {
        return instrumented(IniciarSesionTask.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String usuarioFinal = usuario != null ? usuario : actor.recall("usuarioRegistrado");
        String claveFinal = clave != null ? clave : actor.recall("claveRegistrada");

        actor.attemptsTo(
                Clear.field(PaginaLogin.INP_USUARIO),
                Enter.theValue(usuarioFinal).into(PaginaLogin.INP_USUARIO),
                Clear.field(PaginaLogin.INP_CLAVE),
                Enter.theValue(claveFinal).into(PaginaLogin.INP_CLAVE),
                Click.on(PaginaLogin.BTN_INGRESAR)
        );
    }
}
