package com.jsy_codes.book_lecture_shop.domain.course;

import com.jsy_codes.book_lecture_shop.domain.item.Item;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ItemCourse {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    public ItemCourse( Item item,Course course) {
        this.item = item;
        this.course = course;

    }

}
