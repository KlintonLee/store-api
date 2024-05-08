package com.klinton.store.application;

public abstract class UnitUseCase<IN>{

    public abstract void execute(IN input);
}
