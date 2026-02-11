package com.jsy_codes.book_lecture_shop.domain.course;

import com.jsy_codes.book_lecture_shop.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCourse {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    private int displayOrder;

    public static ItemCourse create(Course course, Item item, int displayOrder) {
        ItemCourse itemCourse = new ItemCourse();
        itemCourse.displayOrder = displayOrder;
        itemCourse.changeCourse(course);
        itemCourse.changeItem(item);
        return itemCourse;
    }
    private void changeCourse(Course course) {
        this.course = course;
        course.getItemCourses().add(this);
    }

    private void changeItem(Item item) {
        this.item = item;
        item.getItemCourses().add(this);
    }

}
