package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    Coach coach;
    Integer count;

    CounterOfTrainings(Coach coach, Integer count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public Integer getCount() {
        return count;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
