package org.example.Tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.Pages.CommonPage;
import org.example.Pages.PaginaInicio;
import org.example.Pages.PaginaRegistro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegistrarTask implements Task {

    private final Map<String, String> datos;
    private final String campoOmitido;

    public RegistrarTask(Map<String, String> datos) {
        this(datos, null);
    }

    public RegistrarTask(Map<String, String> datos, String campoOmitido) {
        this.datos = datos;
        this.campoOmitido = campoOmitido;
    }

    public static Performable conDatos(Map<String, String> datos) {
        return instrumented(RegistrarTask.class, datos);
    }

    public static Performable conDatosOmitiendo(Map<String, String> datos, String campo) {
        return instrumented(RegistrarTask.class, datos, campo.trim());
    }

    public static Performable registrarCuenta(Map<String, String> datos) {
        return Task.where("{0} registra una cuenta nueva en la tienda",
                Click.on(PaginaInicio.BTN_LOGIN_O_REGISTRO),
                Click.on(CommonPage.BTN_CONTINUAR),
                instrumented(RegistrarTask.class, datos),
                Click.on(CommonPage.BTN_CONTINUAR),
                WaitUntil.the(CommonPage.LBL_TITULO_PRINCIPAL, WebElementStateMatchers.containsText("YOUR ACCOUNT HAS BEEN CREATED!"))
                        .forNoMoreThan(15).seconds()
        );
    }

    private String obtenerCampo(String clave) {
        if (clave.equals(campoOmitido)) {
            return "";
        }
        String valor = datos.get(clave);
        return valor == null ? "" : valor.trim();
    }

    private static void llenarCampo(List<Performable> pasos, Target campo, String valor) {
        pasos.add(Clear.field(campo));
        if (!valor.isEmpty()) {
            pasos.add(Enter.theValue(valor).into(campo));
        }
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String correoCrudo = obtenerCampo("email");
        String usuarioBase = obtenerCampo("usuario");
        String clave = obtenerCampo("clave");
        String pais = obtenerCampo("pais");
        String region = obtenerCampo("region");

        // Sufijo unico para evitar usuarios/correos repetidos entre ejecuciones
        String sufijoUnico = Long.toString(System.currentTimeMillis(), 36);
        String usuarioFinal = usuarioBase.isEmpty() ? "" : usuarioBase + "_" + sufijoUnico;
        int indiceArroba = correoCrudo.indexOf('@');
        String correoFinal = indiceArroba > 0
                ? correoCrudo.substring(0, indiceArroba) + "_" + sufijoUnico + correoCrudo.substring(indiceArroba)
                : correoCrudo;

        actor.remember("usuarioRegistrado", usuarioFinal);
        actor.remember("claveRegistrada", clave);

        List<Performable> pasos = new ArrayList<>();
        pasos.add(WaitUntil.the(PaginaRegistro.INP_NOMBRE, WebElementStateMatchers.isVisible()).forNoMoreThan(10).seconds());
        llenarCampo(pasos, PaginaRegistro.INP_NOMBRE, obtenerCampo("nombre"));
        llenarCampo(pasos, PaginaRegistro.INP_APELLIDO, obtenerCampo("apellido"));
        llenarCampo(pasos, PaginaRegistro.INP_EMAIL, correoFinal);
        llenarCampo(pasos, PaginaRegistro.INP_TELEFONO, obtenerCampo("telefono"));
        llenarCampo(pasos, PaginaRegistro.INP_DIRECCION, obtenerCampo("direccion"));
        llenarCampo(pasos, PaginaRegistro.INP_CIUDAD, obtenerCampo("ciudad"));
        pasos.add(SelectFromOptions.byVisibleText(pais).from(PaginaRegistro.SEL_PAIS));
        pasos.add(WaitUntil.the(PaginaRegistro.opcionRegion(region), WebElementStateMatchers.isPresent()).forNoMoreThan(10).seconds());
        pasos.add(SelectFromOptions.byVisibleText(region).from(PaginaRegistro.SEL_REGION));
        llenarCampo(pasos, PaginaRegistro.INP_CODIGO_POSTAL, obtenerCampo("cp"));
        llenarCampo(pasos, PaginaRegistro.INP_USUARIO, usuarioFinal);
        llenarCampo(pasos, PaginaRegistro.INP_CLAVE, clave);
        llenarCampo(pasos, PaginaRegistro.INP_CONFIRMAR_CLAVE, clave);
        pasos.add(Click.on(PaginaRegistro.CHK_PRIVACIDAD));

        actor.attemptsTo(pasos.toArray(new Performable[0]));
    }
}
