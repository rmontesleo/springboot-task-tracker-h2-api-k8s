package com.demo.task.tracker.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Task {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "task_text")
    private String text;
    
    @Column(name = "task_day")
    private String day;
    
    @Column(name = "set_reminder")
    private Boolean reminder;

    public Task(String text, String day, Boolean reminder) {
        this.text = text;
        this.day = day;
        this.reminder = reminder;
    }   


}
