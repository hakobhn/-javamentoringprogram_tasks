module jmp.cloud.service.impl {
    requires jmp.dto;
    requires transitive jmp.bank.api;
    requires transitive jmp.service.api;
    requires transitive jmp.cloud.bank.imp;
    exports jmp.workshop.service.api.impl;
}