module jmp.cloud.bank.imp {
    requires jmp.dto;
    requires transitive jmp.bank.api;
    exports jmp.workshop.bank.api.impl;

}