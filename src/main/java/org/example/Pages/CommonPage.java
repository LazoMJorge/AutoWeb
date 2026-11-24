package org.example.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class CommonPage {

    public static final Target LBL_TITULO_PRINCIPAL = Target.the("Titulo principal")
            .located(By.xpath("//span[@class='maintext']"));

    public static final Target BTN_CONTINUAR = Target.the("Boton Continue")
            .located(By.xpath("//button[@title='Continue']"));

    public static final Target PNL_CONTENIDO = Target.the("Panel de contenido")
            .located(By.cssSelector("div.contentpanel"));

    public static final Target ALERTA_ERROR = Target.the("Alerta de error")
            .located(By.cssSelector("div.alert.alert-error"));
}
