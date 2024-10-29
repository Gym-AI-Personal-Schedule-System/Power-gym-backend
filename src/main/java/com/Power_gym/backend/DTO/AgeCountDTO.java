package com.Power_gym.backend.DTO;



public class AgeCountDTO {
    private Integer age;
    private Long count;

    // Constructor with parameters matching the query
    public AgeCountDTO(Integer age, Long count) {
        this.age = age;
        this.count = count;
    }

    // Getters and Setters
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
