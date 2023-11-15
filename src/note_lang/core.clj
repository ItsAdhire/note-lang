(ns note-lang.core
  (:require [note-lang.audio :as audio]))

(def song 
  [[:e4 :a4 :c5 :e5]
   [:e4 :e5 :c5 :rest]
   (take 4 (repeat :rest))
   [:b5 :a5 :g5 :c5]])

(def song2
  [:e4 :a4 :c5 :e5
   [:e4 :e5] [:c5 :rest] [:b5 :rest] [:g5 :c5]
   :b5 :rest :g5 :c5]) 

;(audio/play song2 30 8800 120 [])
