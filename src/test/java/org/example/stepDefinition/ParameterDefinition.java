package org.example.stepDefinition;

import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

public class ParameterDefinition {

    @ParameterType("usuario")
    public Actor actor(String nombreActor) {
        return OnStage.theActorCalled(nombreActor);
    }
}
