package org.example.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaCompra {

    public static final Target INP_CANTIDAD = Target.the("Cantidad en ficha de producto")
            .located(By.id("product_quantity"));

    public static final Target BTN_AGREGAR_CARRITO = Target.the("Boton Add to Cart")
            .located(By.cssSelector("a.cart"));

    public static final Target TBL_ITEMS_CARRITO = Target.the("Tabla del carrito")
            .located(By.cssSelector("div.cart-info table.table-striped"));

    public static final Target ENLACES_PRODUCTOS_CARRITO = Target.the("Productos en el carrito")
            .located(By.xpath("//div[contains(@class,'product-list')]//table//tr/td[@class='align_left'][1]/a"));

    public static Target campoCantidadPara(String nombreProducto) {
        return Target.the("Cantidad en carrito para " + nombreProducto)
                .located(By.xpath("//div[contains(@class,'product-list')]//tr[td[@class='align_left']/a[normalize-space()='" + nombreProducto + "']]//input[starts-with(@id,'cart_quantity')]"));
    }

    public static Target botonEliminarPara(String nombreProducto) {
        return Target.the("Eliminar del carrito " + nombreProducto)
                .located(By.xpath("//div[contains(@class,'product-list')]//tr[td[@class='align_left']/a[normalize-space()='" + nombreProducto + "']]//a[contains(@href,'remove')]"));
    }

    public static final Target BTN_ACTUALIZAR_CARRITO = Target.the("Boton Update")
            .located(By.id("cart_update"));

    public static final Target BTN_CHECKOUT = Target.the("Boton Checkout")
            .located(By.id("cart_checkout1"));

    public static final Target BTN_CONFIRMAR_ORDEN = Target.the("Boton Confirm Order")
            .located(By.id("checkout_btn"));

    public static final Target LNK_DETALLE_ORDEN = Target.the("Enlace invoice page")
            .located(By.xpath("//div[@class='contentpanel']//a[contains(@href,'invoice')]"));

    public static final Target TBL_PRODUCTOS_ORDEN = Target.the("Tabla de productos de la orden")
            .located(By.xpath("//div[@class='contentpanel']//table[.//th[contains(.,'Product')] and .//th[contains(.,'Quantity')]]"));
}
