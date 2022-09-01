package com.liny.javastream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linyi
 * @date 2022/9/1
 * 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    private User user;
    private Float high;
    private Float weight;
}
