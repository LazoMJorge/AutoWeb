package org.example.Tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.Pages.CommonPage;
import org.example.Pages.PaginaCompra;
import org.example.Pages.PaginaInicio;

public class ComprarTask {

    public static Performable buscarProducto(String producto) {
        return Task.where("{0} busca el producto " + producto,
                Clear.field(PaginaInicio.INP_BUSCADOR),
                Enter.theValue(producto).into(PaginaInicio.INP_BUSCADOR),
                Click.on(PaginaInicio.BTN_BUSCAR)
        );
    }

    public static Performable agregarProductoConCantidad(String cantidad) {
        return Task.where("{0} agrega " + cantidad + " unidad(es) al carrito",
                Clear.field(PaginaCompra.INP_CANTIDAD),
                Enter.theValue(cantidad).into(PaginaCompra.INP_CANTIDAD),
                Click.on(PaginaCompra.BTN_AGREGAR_CARRITO),
                WaitUntil.the(PaginaCompra.TBL_ITEMS_CARRITO, WebElementStateMatchers.isVisible()).forNoMoreThan(10).seconds()
        );
    }

    public static Performable actualizarCantidadEnCarrito(String producto, String nuevaCantidad) {
        return Task.where("{0} actualiza la cantidad de " + producto + " a " + nuevaCantidad,
                Clear.field(PaginaCompra.campoCantidadPara(producto)),
                Enter.theValue(nuevaCantidad).into(PaginaCompra.campoCantidadPara(producto)),
                Click.on(PaginaCompra.BTN_ACTUALIZAR_CARRITO),
                WaitUntil.the(PaginaCompra.campoCantidadPara(producto), WebElementStateMatchers.hasValue(nuevaCantidad))
                        .forNoMoreThan(10).seconds()
        );
    }

    public static Performable eliminarProductoDelCarrito(String producto) {
        return Task.where("{0} elimina " + producto + " del carrito",
                Click.on(PaginaCompra.botonEliminarPara(producto)),
                WaitUntil.the(PaginaCompra.botonEliminarPara(producto), WebElementStateMatchers.isNotPresent()).forNoMoreThan(10).seconds()
        );
    }

    public static Performable confirmarCompra() {
        return Task.where("{0} confirma la orden de compra",
                Click.on(PaginaCompra.BTN_CONFIRMAR_ORDEN),
                WaitUntil.the(CommonPage.LBL_TITULO_PRINCIPAL, WebElementStateMatchers.containsText("YOUR ORDER HAS BEEN PROCESSED!"))
                        .forNoMoreThan(15).seconds()
        );
    }
}
