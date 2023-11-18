(ns note-lang.core
  (:require [note-lang.audio :as audio]))

(def song
  [:c5 :c5 :g5 :e5
   [:c5 :e5] :c5 :g5 :e5
   :c5 :c5 :g5 :e5
   [:g5 :e5] :c5 :e5 :c5])

;; Bug with subdiviision, vector of size 4 takes 2 beats to play 
(def song2
  [:c4 :c#4 :d4 :d#4
   [:e4 :f4] [:f#4 :g4]
   [:g#4 :a4 :a#4 :b4] [:c5 :c4 :c4 :c5]
   [:c5 :c4 :c4 :c5] [:b4 :a#4 :a4 :g#4]
   [:g4 :f#4] [:f4 :e4]
   :d#4 :d4 :c#4 :c4])
  

(audio/save song 5 4400 120 [:ads-linear :ads-linear] "song-ads-sqr.wav") 
(audio/save song 20 4400 120 [:ads-linear] "song-ads.wav") 
(audio/save song 20 4400 120 [] "song.wav") 
;(audio/play song 20 11000 120 []) 
;(audio/play song 20 4400 120 []) 
(audio/play song2 50 4400 120 [:ads-linear]) 
(audio/play song2 50 4400 120 []) 
(audio/play song 50 4400 120 [:ads-linear :ads-linear]) 
