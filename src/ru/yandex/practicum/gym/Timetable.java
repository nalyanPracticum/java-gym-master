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
        Map<Coach, Integer> CoachWithTrainings = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDays : timetable.values()) {
            for (List<TrainingSession> TrainingsOfTime : trainingsOfDays.values()) {
                for (TrainingSession trainingSession : TrainingsOfTime) {
                    Coach coach = trainingSession.getCoach();
                    if (!CoachWithTrainings.containsKey(coach)) {
                        CoachWithTrainings.put(coach, 1);
                    } else {
                        CoachWithTrainings.put(coach, CoachWithTrainings.get(coach) + 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> CountByCoaches = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : CoachWithTrainings.entrySet()) {
            CounterOfTrainings coach = new CounterOfTrainings(entry.getKey(), entry.getValue());
            CountByCoaches.add(coach);
        }

        CountByCoaches.sort(Comparator.comparing(CounterOfTrainings::getCount).reversed());

        return CountByCoaches;
    }

}

