package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDay = timetable.get(dayOfWeek);

        if (trainingsOfDay == null) {
            trainingsOfDay = new TreeMap<>();
            timetable.put(dayOfWeek, trainingsOfDay);
        }

        List<TrainingSession> trainingsOfTime = trainingsOfDay.get(timeOfDay);

        if (trainingsOfTime == null) {
            trainingsOfTime = new ArrayList<>();
            trainingsOfDay.put(timeOfDay, trainingsOfTime);
        }

        trainingsOfTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.containsKey(dayOfWeek) ? timetable.get(dayOfWeek) : new TreeMap<>();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek) && timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            return  timetable.get(dayOfWeek).get(timeOfDay);
        }
        return new ArrayList<>();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachWithTrainings = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDays : timetable.values()) {
            for (List<TrainingSession> trainingsOfTime : trainingsOfDays.values()) {
                for (TrainingSession trainingSession : trainingsOfTime) {
                    Coach coach = trainingSession.getCoach();
                    if (!coachWithTrainings.containsKey(coach)) {
                        coachWithTrainings.put(coach, 1);
                    } else {
                        coachWithTrainings.put(coach, coachWithTrainings.get(coach) + 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> countByCoaches = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachWithTrainings.entrySet()) {
            CounterOfTrainings coach = new CounterOfTrainings(entry.getKey(), entry.getValue());
            countByCoaches.add(coach);
        }

        countByCoaches.sort(Comparator.comparing(CounterOfTrainings::getCount).reversed());

        return countByCoaches;
    }

}

