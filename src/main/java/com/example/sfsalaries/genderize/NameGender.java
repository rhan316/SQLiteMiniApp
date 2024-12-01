package com.example.sfsalaries.genderize;

import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class NameGender {
    private String name;
    private String gender;
    private float probability;
    private Integer count;

    public boolean isMale() {
        return Gender.MALE.equals(this.getGenderType());
    }

    public boolean isFemale() {
        return Gender.FEMALE.equals(this.getGenderType());
    }

    public Gender getGenderType() {
        if (this.gender == null) return Gender.UNKNOWN;
        return Gender.valueOf(this.gender.toUpperCase());
    }
}
