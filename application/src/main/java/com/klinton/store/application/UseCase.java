package com.klinton.store.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN input);
}
