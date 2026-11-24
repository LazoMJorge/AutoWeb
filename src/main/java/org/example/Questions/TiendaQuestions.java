package org.example.Questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.Pages.CommonPage;
import org.example.Pages.PaginaCompra;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TiendaQuestions {

    public static Question<List<String>> nombresProductosCarrito() {
        return actor -> PaginaCompra.ENLACES_PRODUCTOS_CARRITO.resolveAllFor(actor).stream()
                .map(WebElementFacade::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static Question<String> numeroOrdenGenerado() {
        return actor -> {
            Matcher matcher = Pattern.compile("Your order #(\\d+)")
                    .matcher(Text.of(CommonPage.PNL_CONTENIDO).answeredBy(actor));
            return matcher.find() ? matcher.group(1) : "";
        };
    }
}
