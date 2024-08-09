package com.renanvaz.duplicatesfinder.service;


import com.renanvaz.duplicatesfinder.dto.Contact;
import com.renanvaz.duplicatesfinder.dto.MatchResult;
import com.renanvaz.duplicatesfinder.enums.Accuracy;
import com.renanvaz.duplicatesfinder.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.renanvaz.duplicatesfinder.enums.Accuracy.*;
import static com.renanvaz.duplicatesfinder.utils.Utils.isNullOrEmpty;

public class DuplicatesFinderService {

    public void process() throws Exception {

        var filePath = "/tmp/contacts.csv";

        var matchResults = getMatchResults(getContacts(filePath));

        printSampleOutput(matchResults);
        printSummary(matchResults);
    }

    /*
    Verify the accuracy based on score. The score is calculated using contact parameters. Some parameters have more weight than others.
     */
    private Accuracy verifyAccuracy(Contact contact1, Contact contact2) {

        int score = 0;

        if (!isNullOrEmpty(contact1.getEmail()) && !isNullOrEmpty(contact2.getEmail()) && contact1.getEmail().equalsIgnoreCase(contact2.getEmail()))
            score += 10;
        if (!isNullOrEmpty(contact1.getFirstName()) && !isNullOrEmpty(contact2.getFirstName()) && contact1.getFirstName().equalsIgnoreCase(contact2.getFirstName()))
            score += 4;
        if (!isNullOrEmpty(contact1.getLastName()) && !isNullOrEmpty(contact2.getLastName()) && contact1.getLastName().equalsIgnoreCase(contact2.getLastName()))
            score += 4;
        if (!isNullOrEmpty(contact1.getZipCode()) && !isNullOrEmpty(contact2.getZipCode()) && contact1.getZipCode().equals(contact2.getZipCode()))
            score += 2;
        if (!isNullOrEmpty(contact1.getAddress()) && !isNullOrEmpty(contact2.getAddress()) && contact1.getAddress().equalsIgnoreCase(contact2.getAddress()))
            score += 2;

        if (score >= 15)
            return HIGH;
        else if (score >= 8)
            return MEDIUM;
        else if (score >= 2)
            return LOW;

        return NONE;
    }


    /*
   Gets the contacts list
  */
    private List<Contact> getContacts(String filePath) throws Exception {

        var contacts = new ArrayList<Contact>();

        for (var line : Utils.getFileContent(filePath)) {
            var contactAttributes = line.split(";");
            if (!contactAttributes[0].equals("id")) {
                var id = Integer.valueOf(contactAttributes[0]);
                var firstName = contactAttributes[1];
                var lastName = contactAttributes[2];
                var email = contactAttributes[3];
                var zipCode = contactAttributes[4];
                var address = contactAttributes.length == 6 ? contactAttributes[5] : "";

                contacts.add(new Contact(id, firstName, lastName, email, zipCode, address));
            }
        }

        return contacts;
    }

    public List<MatchResult> getMatchResults(List<Contact> contacts) {

        var matchResults = new ArrayList<MatchResult>();
        for (var contact1 : contacts) {
            for (var contact2 : contacts) {
                if (contact1.getId() != contact2.getId()) {
                    var accuracy = verifyAccuracy(contact1, contact2);
                    if (accuracy != NONE)
                        matchResults.add(new MatchResult(contact1.getId(), contact2.getId(), accuracy));
                }
            }
        }
        return matchResults;
    }

    private void printSummary(List<MatchResult> matchResults) {

        var summary = new HashMap<String, Integer>();
        for (var matchResult : matchResults) {
            if (summary.get(matchResult.getAccuracy().toString()) == null) {
                summary.put(matchResult.getAccuracy().toString(), 1);
            } else {
                summary.put(matchResult.getAccuracy().toString(), summary.get(matchResult.getAccuracy().toString()) + 1);
            }
        }
        System.out.println("");
        System.out.println("Summary: " + summary);
    }

    /*
    Prints a sample output. This method does not print the entire result. If you want to see the complete result. You can look at printSummary method
     */
    private void printSampleOutput(List<MatchResult> matchResults) {

        for (var matchResult : matchResults) {
            if (new Random().nextInt(10) == 0)
                System.out.printf("ContactID Source: %s, ContactID Match: %s, Accuracy: %s %n", matchResult.getSourceId(), matchResult.getMatchId(), matchResult.getAccuracy());
        }
    }
}
