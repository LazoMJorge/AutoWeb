package org.example.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaRegistro {

    public static final Target INP_NOMBRE = Target.the("Campo First Name")
            .located(By.id("AccountFrm_firstname"));

    public static final Target INP_APELLIDO = Target.the("Campo Last Name")
            .located(By.id("AccountFrm_lastname"));

    public static final Target INP_EMAIL = Target.the("Campo Email")
            .located(By.id("AccountFrm_email"));

    public static final Target INP_TELEFONO = Target.the("Campo Telephone")
            .located(By.id("AccountFrm_telephone"));

    public static final Target INP_DIRECCION = Target.the("Campo Address 1")
            .located(By.id("AccountFrm_address_1"));

    public static final Target INP_CIUDAD = Target.the("Campo City")
            .located(By.id("AccountFrm_city"));

    public static final Target SEL_PAIS = Target.the("Selector Country")
            .located(By.id("AccountFrm_country_id"));

    public static final Target SEL_REGION = Target.the("Selector Region / State")
            .located(By.id("AccountFrm_zone_id"));

    public static Target opcionRegion(String region) {
        return Target.the("Opcion de region " + region)
                .located(By.xpath("//select[@id='AccountFrm_zone_id']/option[normalize-space()='" + region + "']"));
    }

    public static final Target INP_CODIGO_POSTAL = Target.the("Campo ZIP / Post Code")
            .located(By.id("AccountFrm_postcode"));

    public static final Target INP_USUARIO = Target.the("Campo Login Name")
            .located(By.id("AccountFrm_loginname"));

    public static final Target INP_CLAVE = Target.the("Campo Password")
            .located(By.id("AccountFrm_password"));

    public static final Target INP_CONFIRMAR_CLAVE = Target.the("Campo Password Confirm")
            .located(By.id("AccountFrm_confirm"));

    public static final Target CHK_PRIVACIDAD = Target.the("Checkbox Privacy Policy")
            .located(By.id("AccountFrm_agree"));
}
