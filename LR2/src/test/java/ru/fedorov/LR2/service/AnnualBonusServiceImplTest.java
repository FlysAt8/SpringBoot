package ru.fedorov.LR2.service;

import org.junit.jupiter.api.Test;

import ru.fedorov.LR2.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AnnualBonusServiceImplTest {
    
    @Test
    void calculate() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_forManager() {
        Positions position = Positions.TL;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();
        double annual = service.calculate(position, salary, bonus, workDays);
        double result = service.calculateQuarterlyBonus(position, salary, bonus, workDays);

        double expected = annual / 4;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_forNonManager_throwsException() {

        Positions position = Positions.DEV;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        assertThatThrownBy(() ->
                new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus, workDays)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
