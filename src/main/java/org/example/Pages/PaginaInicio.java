package org.example.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.pages.PageObject;

public class PaginaInicio extends PageObject {

    public static final Target BTN_LOGIN_O_REGISTRO = Target.the("Enlace Login or register")
            .located(By.xpath("//ul[@id='customer_menu_top']//a[contains(text(),'Login or register')]"));

    public static final Target BTN_CERRAR_SESION = Target.the("Enlace Logoff")
            .located(By.xpath("//ul[@class='side_account_list']//a[contains(text(),'Logoff')]"));

    public static final Target INP_BUSCADOR = Target.the("Buscador")
            .located(By.id("filter_keyword"));

    public static final Target BTN_BUSCAR = Target.the("Boton buscar")
            .located(By.cssSelector("div.button-in-search"));
}
