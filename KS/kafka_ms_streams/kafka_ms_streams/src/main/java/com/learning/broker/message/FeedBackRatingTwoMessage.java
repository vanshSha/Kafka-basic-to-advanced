package com.learning.broker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackRatingTwoMessage {

    private String location;

    private double averageRating;

    private Map<Integer, Long> ratingMap;
}
