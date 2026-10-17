package ru.fedorov.LR2.service;

import ru.fedorov.LR2.model.Positions;

public interface AnnualBonusService {

    double calculate(Positions positions, double salary, double bonus, int workDays);

    double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays);
}
