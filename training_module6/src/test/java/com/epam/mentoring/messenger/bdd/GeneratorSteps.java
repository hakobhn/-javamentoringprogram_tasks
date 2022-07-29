/*
 * (C) Copyright 2017 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.epam.mentoring.messenger.bdd;

import com.epam.mentoring.messenger.service.TemplateGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratorSteps implements En {

    static final Logger log = LoggerFactory.getLogger(GeneratorSteps.class);

    @Autowired
    private TemplateGenerator templateGenerator;

    private List<List<String>> valuesList;
    private List<Map<String, String>> values = new ArrayList<>();
    private Boolean isGenerated = true;

    public GeneratorSteps() {

        Given("user wants to generate an email content with replacing the following placeholders",
                (DataTable placeholdersDt) -> {
            // DataTable converted into map
            valuesList = placeholdersDt.asLists();
            Map<String, String> data;

            for (int i = 1; i < valuesList.size(); i++) {
                data = new HashMap<>();
                for (int j = 0; j < valuesList.size(); j++) {
                    data.put(valuesList.get(0).get(j), valuesList.get(i).get(j));
                }
                values.add(data);
            }

            log.debug("Datatable: {}", valuesList);
            log.debug("values: {}", values);
        });

        When("user generates the new emails {string}", (String testContext) -> {
            // passing data to template generator
            for (Map<String, String> data : values) {
                isGenerated = isGenerated && !templateGenerator.generate(data).isEmpty();
            }
        });

        Then("the generation {string}", (String expectedResult) -> {
            assertTrue(isGenerated);
        });

    }

}
