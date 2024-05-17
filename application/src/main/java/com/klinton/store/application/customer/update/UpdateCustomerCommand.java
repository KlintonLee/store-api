package com.klinton.store.application.customer.update;

public record UpdateCustomerCommand(
        String id,
        String name,
        String email,
        String phone,
        boolean active
) {
    public static UpdateCustomerCommand of(
            String id,
            String name,
            String email,
            String phone,
            boolean active
    ) {
        return new UpdateCustomerCommand(id, name, email, phone, active);
    }
}
