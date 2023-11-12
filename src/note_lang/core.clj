(ns note-lang.core
  (:require [note-lang.audio :as audio]))

(def note->freq
  {:e4 329.63
   :a4 440
   :c5 523.25
   :e5 659.25
   :g5 783.99
   :b5 987.77
   :a5 880
   :rest 0})

(def song 
  [[:e4 :a4 :c5 :e5]
   [:e4 :e5 :c5 :rest]
   (take 4 (repeat :rest))
   [:b5 :a5 :g5 :c5]])

(def song2
  [:e4 :a4 :c5 :e5
   [:e4 :e5] [:c5 :rest] [:b5 :rest] [:g5 :c5]
   :b5 :rest :g5 :c5]) 

(audio/play song2 30 4400 210)
