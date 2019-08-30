package com.example.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTask {
    @Id
    @GeneratedValue(strategy =IDENTITY )
    private Long id;
    private String name;
    private int status;
    private Long sendTime;
}
