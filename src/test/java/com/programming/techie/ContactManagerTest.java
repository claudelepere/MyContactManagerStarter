package com.programming.techie;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest
{

  ContactManager contactManager;

  @BeforeAll
  public void setupAll() {
    System.out.println("Should print before all tests");
  }

  @BeforeEach
  public void setup() {
    contactManager = new ContactManager();
  }

  @AfterEach
  public void tearDown() {
    System.out.println("Should execute after each test");
  }

  @AfterAll
  public void tearDownAll() {
    System.out.println("Should execute at the end of the test");
  }

  @Test
  public void shouldCreateContact() {
    contactManager.addContact("John", "Doe", "0123456789");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
    Assertions.assertTrue(contactManager.getAllContacts().stream()
        .filter(contact -> contact.getFirstName().equals("John") &&
                           contact.getLastName().equals("Doe") &&
                           contact.getPhoneNumber().equals("0123456789"))
        .findAny()
        .isPresent());
  }

  @Test
  @DisplayName("Should not create contact when firstName is null")
  public void shouldThrowRunTimeExceptionWhenFirstNameIsNull() {
    Assertions.assertThrows(RuntimeException.class, () -> contactManager.addContact(null, "Doe", "0123456789"));
  }

  @Test
  @DisplayName("Should not create contact when lastName is null")
  public void shouldThrowRunTimeExceptionWhenLasttNameIsNull() {
    Assertions.assertThrows(RuntimeException.class, () -> contactManager.addContact("John", null, "0123456789"));
  }

  @Test
  @DisplayName("Should not create contact when phoneNumber is null")
  public void shouldThrowRunTimeExceptionWhenPhoneNumberIsNull() {
    Assertions.assertThrows(RuntimeException.class, () -> contactManager.addContact("John", "Doe", null));
  }

  @Test
  @DisplayName("Should create contact only on MAC OS")
  @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Enabled only on MAC OS")
  public void shouldCreateContactOnlyOnMAC() {
    contactManager.addContact("John", "Doe", "0123456789");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
    Assertions.assertTrue(contactManager.getAllContacts().stream()
        .filter(contact -> contact.getFirstName().equals("John") &&
                           contact.getLastName().equals("Doe") &&
                           contact.getPhoneNumber().equals("0123456789"))
        .findAny()
        .isPresent());
  }

  @Test
  @DisplayName("Should not create contact on Windows OS")
  @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
  public void shouldNotCreateContactOnWindows() {
    System.out.println("Windows");
    contactManager.addContact("John", "Doe", "0123456789");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
    Assertions.assertTrue(contactManager.getAllContacts().stream()
        .filter(contact -> contact.getFirstName().equals("John") &&
                           contact.getLastName().equals("Doe") &&
                           contact.getPhoneNumber().equals("0123456789"))
        .findAny()
        .isPresent());
  }

  @Test
  @DisplayName("Test contact creation on developer machine")
  public void shouldTestContactCreationOnDEV() {
    Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
    contactManager.addContact("John", "Doe", "0123456789");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
  }

  @Nested
  class RepeatedNestedTest
  {
    @DisplayName("Repeat contact creation test 5 times")
    @RepeatedTest(value = 5, name = "Repeating contact creation test {currentRepetition} of {totalRepetitions}")
    public void shouldTestCreationRepeatedly() {
      contactManager.addContact("John", "Doe", "0123456789");
      Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
      Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
  }

  @Nested
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  class ParameterizedNestedTest
  {
    @DisplayName("Phone number should match the required format")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "0123456789", "0123456789"} )
    public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
      contactManager.addContact("John", "Doe", phoneNumber);
      Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
      Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Method source case: phone number should match the required format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber) {
      contactManager.addContact("John", "Doe", phoneNumber);
      Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
      Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("CSV source case: phone number should match the required format")
    @ParameterizedTest
    @CsvSource({"0123456789", "0123456789", "0123456789"})
    public void shouldTestContactCreationUsingCSVSource(String phoneNumber) {
      contactManager.addContact("John", "Doe", phoneNumber);
      Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
      Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("CSV file source case: phone number should match the required format")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestContactCreationUsingCSVFileSource(String phoneNumber) {
      contactManager.addContact("John", "Doe", phoneNumber);
      Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
      Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    private List<String> phoneNumberList() {
      return Arrays.asList("0123456789", "0123456789", "0123456789");
    }
  }

}
