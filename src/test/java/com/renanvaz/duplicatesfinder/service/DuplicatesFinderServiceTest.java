package com.renanvaz.duplicatesfinder.service;

import com.renanvaz.duplicatesfinder.dto.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static com.renanvaz.duplicatesfinder.enums.Accuracy.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DuplicatesFinderServiceTest {

    private List<Contact> contacts = new ArrayList<>();

    @BeforeEach
    public void initContacts() {
        contacts.add(new Contact(1, "Renan", "Vaz", "renan.vaz@gmail.com", "12345", "Rua 1"));
        contacts.add(new Contact(2, "Ronaldinho", "Gaucho", "teste@example.com", "12345", "Rua 1"));
        contacts.add(new Contact(3, "Menino", "Ney", "teste@example.com", "12345", "Rua 2"));
        contacts.add(new Contact(4, "Raimundo", "Vaz", "renan.vaz@gmail.com", "12345", "Rua 2"));
    }

    @Test
    public void getMatchResultsTest() {

        var matchResults = new DuplicatesFinderService().getMatchResults(contacts);

        Assertions.assertEquals(1, matchResults.get(0).getSourceId());
        Assertions.assertEquals(2, matchResults.get(0).getMatchId());
        Assertions.assertEquals(LOW, matchResults.get(0).getAccuracy());

        Assertions.assertEquals(1, matchResults.get(1).getSourceId());
        Assertions.assertEquals(3, matchResults.get(1).getMatchId());
        Assertions.assertEquals(LOW, matchResults.get(1).getAccuracy());

        Assertions.assertEquals(1, matchResults.get(2).getSourceId());
        Assertions.assertEquals(4, matchResults.get(2).getMatchId());
        Assertions.assertEquals(HIGH, matchResults.get(2).getAccuracy());

        Assertions.assertEquals(2, matchResults.get(3).getSourceId());
        Assertions.assertEquals(1, matchResults.get(3).getMatchId());
        Assertions.assertEquals(LOW, matchResults.get(3).getAccuracy());

        Assertions.assertEquals(2, matchResults.get(4).getSourceId());
        Assertions.assertEquals(3, matchResults.get(4).getMatchId());
        Assertions.assertEquals(MEDIUM, matchResults.get(4).getAccuracy());
    }
}