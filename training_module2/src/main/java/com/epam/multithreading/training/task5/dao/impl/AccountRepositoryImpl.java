package com.epam.multithreading.training.task5.dao.impl;

import com.epam.multithreading.training.task5.NotFoundException;
import com.epam.multithreading.training.task5.dao.AccountRepository;
import com.epam.multithreading.training.task5.dao.entity.Account;
import com.epam.multithreading.training.task5.util.AccountSerializer;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {

    private static final String ACCOUNT_PATH = "accounts";

    @Override
    public Account save(Account account) {
        account.setUuid(UUID.randomUUID().toString());
        account.getBankAccounts().stream()
                .forEach(bnk -> bnk.setUuid(UUID.randomUUID().toString()));
        JSONObject json = AccountSerializer.serialize(account);

        Path directory = Paths.get(ACCOUNT_PATH);
        if (! Files.exists(directory)){
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path path = Path.of(ACCOUNT_PATH + File.separator + account.getUuid() + ".json");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(ACCOUNT_PATH+ File.separator + account.getUuid()+".json"))) {
            out.write(json.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findById(String uuid) {

        Path path = Path.of(ACCOUNT_PATH + File.separator + uuid + ".json");
        if (!Files.exists(path)) {
            throw new NotFoundException("Account with uuid: "+uuid+" not found");
        }
        try {
            String content = Files.readString(path, Charset.forName("UTF-8"));
            // Convert JSON string to JSONObject
            JSONObject json = new JSONObject(content);
            return AccountSerializer.deserialize(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String uuid) {
        Path path = Path.of(ACCOUNT_PATH + File.separator + uuid + ".json");
        if (!Files.exists(path)) {
            throw new NotFoundException("Account with uuid: "+uuid+" not found");
        }
        try {
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> findAll() {
        Path path = Path.of(ACCOUNT_PATH + File.separator);
        try {
            return Files.list(path)
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(File::toPath)
                    .map(f -> {
                        try {
                            return Files.readString(f, Charset.forName("UTF-8"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(JSONObject::new)
                    .map(AccountSerializer::deserialize)
                    .collect(Collectors.toUnmodifiableList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAll() {
        Path path = Path.of(ACCOUNT_PATH);
        try {
            Files.list(path)
                    .forEach(f -> {
                        try {
                            Files.delete(f);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
