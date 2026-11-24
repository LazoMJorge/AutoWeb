package org.example.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaLogin {

    public static final Target INP_USUARIO = Target.the("Usuario")
            .located(By.id("loginFrm_loginname"));

    public static final Target INP_CLAVE = Target.the("Clave")
            .located(By.id("loginFrm_password"));

    public static final Target BTN_INGRESAR = Target.the("Boton Login")
            .located(By.xpath("//button[@title='Login']"));
}
