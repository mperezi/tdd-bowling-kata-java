---
title: tdd-bowling-kata
description: An implementation of the classic bowling kata for a TDD exercise
class: beginners
toc:
  things-to-note: "Things to note"
  story-specification: "Story Specification"
  test-cases: "Test Cases"
order: 2
image: /assets/images/cards/beginner.png
---

## Things to note

1.	No judgment, Nothing that happens inside this room will be reported to anyone outside it. The point is to try things out and learn from getting it wrong.
1.	No governance, you can pick whatever tools and programming languages you like.
1.	There are no right answers, it’s ok to come to different solutions. We will make various trade-offs along the way.
1.	Red, Green, Refactor.
1.	Code coverage statistics as a bonus.


## Story Specification

1.	The game consists of 10 frames.  
1.	In each frame the player has two opportunities to knock down 10 pins.  
1.	The score for the frame is the total number of pins knocked down, plus bonuses for strikes and spares.
1.	A spare is when the player knocks down all 10 pins in two tries.  The bonus for that frame is the number of pins knocked down by the next roll.
1.	A strike is when the player knocks down all 10 pins on his first try.  The bonus for that frame is the value of the next two balls rolled.
1.	In the tenth frame a player who rolls a spare or strike is allowed to roll the extra balls to complete the frame.  However no more than three balls can be rolled in tenth frame.


## Test Cases

1.	The worst player in the world, hits all gutter balls, scores zero.
1.	The most consistent player in the world, hits one pin every time, scores 20.
1.	Can calculate a spare correctly, followed by a 3 score, scores 16.
1.	Can calculate a strike correctly, followed by a 3 and another 3, scores 22.
1.	Bowls a perfect game, scores 300.



