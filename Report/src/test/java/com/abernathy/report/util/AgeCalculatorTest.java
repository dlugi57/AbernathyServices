package com.abernathy.report.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AgeCalculatorTest {

    @Test
    public void testCalculateAge_Success() {
        // setup
        LocalDate birthDate = LocalDate.of(1961, 5, 17);
        // exercise
        int actual = AgeCalculator.calculateAge(birthDate);
        // assert
        assertThat(actual).isEqualTo(59);

    }

    @Test
    public void testCalculateAge_Null() {

        // exercise
        int actual = AgeCalculator.calculateAge(null);
        // assert
        assertThat(actual).isEqualTo(0);

    }
}