package ru.fedorov.LR2.service;

import java.time.Year;

import org.springframework.stereotype.Service;

import ru.fedorov.LR2.model.Positions;

@Service 
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override 
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int daysInYear = Year.now().length();
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays) {
        if (!positions.isManager()) {
            throw new IllegalArgumentException(positions + " — не менеджер");
        }
        return calculate(positions, salary, bonus, workDays) / 4;
    }

}
