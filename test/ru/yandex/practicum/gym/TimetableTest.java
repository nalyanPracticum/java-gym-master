package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsMon = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingsMon.keySet().size());

        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsTu = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, trainingsTu.keySet().size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsMon = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingsMon.keySet().size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsThu = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        NavigableSet<TimeOfDay> times = trainingsThu.navigableKeySet();
        int index = 0;

        for (TimeOfDay timeOfDay : times) {
            if (index == 0) {
                Assertions.assertEquals("13:0", timeOfDay.toString());
            } else {
                Assertions.assertEquals("20:0", timeOfDay.toString());
            }
            index++;
        }
        Assertions.assertEquals(2, index);

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsTu = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, trainingsTu.keySet().size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> trainingsMon13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingsMon13.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> trainingsMon14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertEquals(0, trainingsMon14.size());
    }

    @Test
    void testAddNewTrainingSessionForOneTime() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);

        Group group2 = new Group("Аэробика для детей", Age.CHILD, 30);
        Coach coach2 = new Coach("Петров", "Николай", "Сергеевич");
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(secondTrainingSession);

        //Проверить, что в понедельник в 13:00 Начнется 2 занятия
        List<TrainingSession> trainingsMon13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(2, trainingsMon13.size());
    }

    @Test
    void testAddNewTrainingSessionSortTimeOfDay() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession13hours = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession15hours = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        TrainingSession trainingSession14hours = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(trainingSession13hours);
        timetable.addNewTrainingSession(trainingSession15hours);

        //Проверить, что список тренировок по времени отсортирован
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int index = 0;
        for (TimeOfDay timeOfDay : trainingSessionsForDay.keySet()) {
            if (index == 0) {
                Assertions.assertEquals(new TimeOfDay(13, 0), timeOfDay);
            }
            if (index == 1) {
                Assertions.assertEquals(new TimeOfDay(15, 0), timeOfDay);
            }
            index++;
        }

        // Проверить, что после добавления тренировки, список будет отсотирован
        timetable.addNewTrainingSession(trainingSession14hours);
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDayNew = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int indexNew = 0;
        for (TimeOfDay timeOfDay : trainingSessionsForDay.keySet()) {
            if (indexNew == 0) {
                Assertions.assertEquals(new TimeOfDay(13, 0), timeOfDay);
            }
            if (indexNew == 1) {
                Assertions.assertEquals(new TimeOfDay(14, 0), timeOfDay);
            }
            if (indexNew == 2) {
                Assertions.assertEquals(new TimeOfDay(15, 0), timeOfDay);
            }
            indexNew++;
        }
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Николай", "Сергеевич");
        Coach coach3 = new Coach("Иванов", "Иван", "Александрович");
        TrainingSession singleTrainingSession1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        List<CounterOfTrainings> coachCount = timetable.getCountByCoaches();

        int index = 0;
        for (CounterOfTrainings counterOfTrainings : coachCount) {

            // Проверить, что первым в списке тренеров будет тренер с максимальным количество тренировок (Петров)
            if (index == 0) {
                Assertions.assertEquals(coach2, counterOfTrainings.getCoach());
            }
            // Проверить, что вторым в списке тренеров будет тренер с максимальным количество тренировок (Васильев)
            if (index == 1) {
                Assertions.assertEquals(coach1, counterOfTrainings.getCoach());
            }
            // Проверить, что последним в списке тренеров будет тренер с минимальным количество тренировок (Иванов)
            if (index == 2) {
                Assertions.assertEquals(coach3, counterOfTrainings.getCoach());
            }

            // Проверить, что у тренера Васильева 1 тренировка
            if (counterOfTrainings.getCoach().equals(coach1)) {
                Assertions.assertEquals(1, counterOfTrainings.getCount());

                // Проверить, что у тренера Петрова 2 тренировки
            }
            if (counterOfTrainings.getCoach().equals(coach2)) {
                Assertions.assertEquals(2, counterOfTrainings.getCount());

                // Проверить, что у тренера Иванова нет тренировок
            }
            if (counterOfTrainings.getCoach().equals(coach3)) {
                Assertions.assertEquals(0, counterOfTrainings.getCount());
            }

            index++;
        }
    }
}
