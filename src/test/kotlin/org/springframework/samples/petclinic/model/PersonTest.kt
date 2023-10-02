package org.springframework.samples.petclinic.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PersonTests {
  @Test
  fun `fullName with middle name`() {
    val person = Person()
    person.firstName = "John"
    person.middleName = "Doe"
    person.lastName = "Smith"

    assertThat(person.fullName()).isEqualTo("John D. Smith")
  }

  @Test
  fun `fullName without middle name`() {
    val person = Person()
    person.firstName = "Jane"
    person.lastName = "Doe"

    assertThat(person.fullName()).isEqualTo("Jane Doe")
  }
}
