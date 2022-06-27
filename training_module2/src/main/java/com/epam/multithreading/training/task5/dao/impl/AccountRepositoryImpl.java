package com.epam.multithreading.training.task5.dao.impl;

import com.epam.multithreading.training.task5.dao.AccountRepository;
import com.epam.multithreading.training.task5.dao.entity.Account;
import com.epam.multithreading.training.task5.exception.InvalidDataSubmittedException;
import com.epam.multithreading.training.task5.exception.NotFoundException;
import com.epam.multithreading.training.task5.exchange.ExchangeProcessor;
import com.epam.multithreading.training.task5.util.AccountSerializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    private static final String ACCOUNT_PATH = "accounts";

    @Override
    public Account save(Account account) {
        checkRootPath();
        if (account == null) {
            throw new InvalidDataSubmittedException("Account is null...");
        }
        account.setUuid(UUID.randomUUID().toString());
        account.getBankAccounts().stream()
                .forEach(bnk -> bnk.setUuid(UUID.randomUUID().toString()));
        JSONObject json = AccountSerializer.serialize(account);

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
        return account;
    }

    @Override
    public Account update(Account account) {
        checkRootPath();
        if (account == null) {
            throw new InvalidDataSubmittedException("Account is null...");
        }
        Account old = findById(account.getUuid());
        if (old == null) {
            throw new NotFoundException("Account with uuid: "+old.getUuid()+" not found...");
        }
        Path path =  Path.of(ACCOUNT_PATH+ File.separator + account.getUuid()+".json");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Path oldPath = Path.of(ACCOUNT_PATH + File.separator + account.getUuid() +
                "-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))+"-history.json");
        try (PrintWriter out = new PrintWriter(new FileWriter(oldPath.toFile()))) {
            out.write( Files.readString(path, Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        delete(old.getUuid());
        JSONObject json = AccountSerializer.serialize(account);

        path = Path.of(ACCOUNT_PATH + File.separator + account.getUuid() + ".json");
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
        return account;
    }

    @Override
    public Account findById(String uuid) {
        checkRootPath();
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
        checkRootPath();
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
        checkRootPath();
        Path path = Path.of(ACCOUNT_PATH + File.separator);
        try {
            return Files.list(path)
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(File::toPath)
                    .map(f -> {
                        String result ="";
                        try {
                            result = Files.readString(f, Charset.forName("UTF-8"));
                        } catch (IOException e) {
                            logger.error("Caught exception on reading file. Error: {}", e.getLocalizedMessage());
                            throw new RuntimeException(e);
                        }
                        return result;
                    })
                    .map(JSONObject::new)
                    .map(AccountSerializer::deserialize)
                    .collect(Collectors.toUnmodifiableList());
        } catch (Exception e) {
            logger.error("Caught exception on reading file. Error: {}", e.getLocalizedMessage());
        }
        return List.of();
    }

    @Override
    public void deleteAll() {
        checkRootPath();
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

    private void checkRootPath() {
        Path rootDir = Path.of(ACCOUNT_PATH);
        if (! Files.exists(rootDir)){
            try {
                Files.createDirectories(rootDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
